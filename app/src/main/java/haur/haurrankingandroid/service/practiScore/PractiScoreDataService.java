package haur.haurrankingandroid.service.practiScore;

import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.MatchScore;
import haur.haurrankingandroid.domain.ScoreCard;
import haur.haurrankingandroid.domain.Stage;
import haur.haurrankingandroid.domain.StageScore;
import haur.haurrankingandroid.service.persistence.SaveMatchTask;
import haur.haurrankingandroid.service.file.FileService;

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
			new SaveMatchTask(null).execute(match);
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

}

