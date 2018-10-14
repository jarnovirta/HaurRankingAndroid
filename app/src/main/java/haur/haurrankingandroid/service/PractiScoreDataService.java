package haur.haurrankingandroid.service;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Division;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.MatchScore;
import haur.haurrankingandroid.domain.ScoreCard;
import haur.haurrankingandroid.domain.Stage;
import haur.haurrankingandroid.domain.StageScore;

/**
 * Created by Jarno on 13.10.2018.
 */

public class PractiScoreDataService {
	public static void importFromFile(final Uri uri) {

		Object[] exportFileData = FileService.readPractiScoreExportFile(uri);

		Match match = (Match) exportFileData[0];

		MatchScore matchScore = (MatchScore) exportFileData[1];

		// List all ScoreCards for match
		List<ScoreCard> cards = new ArrayList<ScoreCard>();

		for (StageScore ss : matchScore.getStageScores()) {
			Stage stage = getStage(match, ss.getStageUuid());
			prepareScoreCards(ss.getScoreCards(), match, stage);
			cards.addAll(ss.getScoreCards());
		}
		if (cards.size() > 0) {
			new SaveDataTask(match, cards).execute();
		}
	}

	private static void prepareScoreCards(List<ScoreCard> cards, Match match, Stage stage) {
		if (stage.getClassifierCode() != null) {
			Classifier classifier = Classifier.fromPractiScoreCode(stage.getClassifierCode());
			for (ScoreCard card : cards) {
				card.setClassifier(classifier);
				card.setCompetitor(getCompetitor(match, card.getCompetitorPractiScoreId()));
				card.setHitsAndPoints();
			}
		}
	}
	private static Stage getStage(Match match, String stagePractiScoreId) {
		for (Stage stage : match.getStages()) {
			if (stage.getPractiScoreId().equals(stagePractiScoreId)) return stage;
		}
		return null;
	}

	private static Competitor getCompetitor(Match match, String competitorPractiScoreId) {
		for (Competitor comp : match.getCompetitors()) {
			if (comp.getPractiScoreId().equals(competitorPractiScoreId)) return comp;
		}
		return null;
	}
	public static class SaveDataTask extends AsyncTask<Void, Void, Integer> {

		private Match match;
		private List<ScoreCard> scoreCards;

		public SaveDataTask(Match match, List<ScoreCard> cards) {
			this.match = match;
			this.scoreCards = cards;
		}
		@Override
		protected Integer doInBackground(Void... params) {
			try {
				AppDatabase db = AppDatabase.getDatabase();
				match.setId(db.matchDao().insert(match));
				if (match.getCompetitors() != null && match.getCompetitors().size() > 0) {
					saveCompetitors(match.getCompetitors(), match.getId());
				}
				if (scoreCards != null && scoreCards.size() > 0) {
					saveScoreCards(scoreCards, match);
				}
			}
			catch (Exception e) {
				Log.e("SaveDataTask", e.getMessage(), e);
			}
			return null;
		}

		private static void saveScoreCards(List<ScoreCard> scoreCards, Match match) {
			AppDatabase db = AppDatabase.getDatabase();
			for (ScoreCard card : scoreCards) {
				card.setCompetitorId(card.getCompetitor().getId());
				card.setMatchId(match.getId());
			}
			db.scoreCardDao().insertAll(scoreCards);
		}
		private static void saveCompetitors(List<Competitor> competitors, Long matchId) {
			AppDatabase db = AppDatabase.getDatabase();

			for (Competitor comp : competitors) {
				Competitor existingCompetitor = db.competitorDao().findByName(comp.getFirstName(),
						comp.getLastName());
				if (existingCompetitor != null) comp.setId(existingCompetitor.getId());
				else {
					comp.setId(db.competitorDao().insert(comp));
				}
			}
		}
	}
}

