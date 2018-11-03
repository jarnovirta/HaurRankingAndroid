package haur.haurrankingandroid.service.practiScore;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.MatchScore;
import haur.haurrankingandroid.domain.ScoreCard;
import haur.haurrankingandroid.domain.Stage;
import haur.haurrankingandroid.domain.StageScore;
import haur.haurrankingandroid.service.task.SaveMatchTask;
import haur.haurrankingandroid.service.file.FileService;

/**
 * Created by Jarno on 13.10.2018.
 */

public class PractiScoreDataService {

	public static Match readMatchFromFile(final Uri uri) {
		Match match = (Match) FileService.readPractiScoreExportFile(uri)[0];
		MatchScore matchScore = (MatchScore) FileService.readPractiScoreExportFile(uri)[1];
		if (matchScore.getStageScores() != null) {

			List<ScoreCard> cards = new ArrayList<>();

			for (StageScore ss : matchScore.getStageScores()) {
				Stage stage = getStage(match.getStages(), ss.getStageUuid());
				if (stage != null && !stage.isDeleted()) {
					if (ss.getScoreCards() != null) {
						for (ScoreCard card : ss.getScoreCards()) {
							card.setCompetitor(getCompetitor(match.getCompetitors(), card.getCompetitorPractiScoreId()));
							if (!card.getCompetitor().isDisqualified()) {
								card.setHitsAndPoints();
								card.setStagePractiScoreId(ss.getStageUuid());
								card.setDivision(card.getCompetitor().getDivision());
								cards.add(card);
							}
						}
					}
				}
			}
			match.setScoreCards(cards);
		}
		return match;
	}

	private static Competitor getCompetitor(List<Competitor> comps, String practiScoreId) {
		for (Competitor comp : comps) if (comp.getPractiScoreId().equals(practiScoreId)) return comp;
		return null;
	}

	private static Stage getStage(List<Stage> stages, String practiScoreId) {
		for (Stage stage : stages) if (stage.getPractiScoreId().equals(practiScoreId)) return stage;
		return null;
	}
}

