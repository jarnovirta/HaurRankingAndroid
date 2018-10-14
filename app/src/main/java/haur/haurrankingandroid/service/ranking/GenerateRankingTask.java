package haur.haurrankingandroid.service.ranking;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Division;
import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.DivisionRankingRow;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.domain.ScoreCard;

/**
 * Created by Jarno on 14.10.2018.
 */

public class GenerateRankingTask extends AsyncTask<Void, Void, Ranking> {

	private GenerateRankingResponseHandler handler;

	public GenerateRankingTask(GenerateRankingResponseHandler handler) {
		this.handler = handler;
	}
	@Override
	protected Ranking doInBackground(Void... args) {
		Ranking ranking = new Ranking();
		ranking.setDivisionRankings(new ArrayList<DivisionRanking>());

		for (Division division : Division.values()) {
			Log.d("TEST", "\n\n\t**** DIVISION: " + division.toString() + " **** ");

			DivisionRanking divRank = generateDivisionRanking(division);
//			setMatchesToCards(scoreCards);

//			Map<Competitor, List<ScoreCard>> scoreCardMap = mapCardsByCompetitor(scoreCards);
//			sortAndFilterScoreCardsMap(scoreCardMap);

//			ranking.getDivisionRankings().add(generateDivisionRanking(division, scoreCardMap));

		}
		return null;
	}

	private DivisionRanking generateDivisionRanking(Division division) {

		AppDatabase db = AppDatabase.getDatabase();

		// Get classifiers with min. 2 results
		List<Classifier> validClassifiers = db.scoreCardDao().getValidClassifiers(division);

		// Average of top two hitfactors for a classifier
		Map<Classifier, Double> classifierTopHitFactorsAveragesMap = new HashMap<>();

		for (Classifier classifier : validClassifiers) {
			Log.d("TEST", classifier.toString() + " AVERAGE: " + db.scoreCardDao().getTopTwoHitFactorsAverage(division, classifier));
			classifierTopHitFactorsAveragesMap.put(classifier,
					db.scoreCardDao().getTopTwoHitFactorsAverage(division, classifier));
		}

		// Competitors with results in valid classifiers
		List<Competitor> competitors = db.competitorDao().getCompetitorsWithResults(division, validClassifiers);

		Map<Competitor, Double> competitorRelativeResultsAverages = new HashMap<>();

		for (Competitor comp : competitors) {
			List<ScoreCard> cards = db.scoreCardDao().getForRanking(division, comp.getId(), validClassifiers);

			List<Double> competitorRelativeResults = new ArrayList<>();
			for (ScoreCard card : cards) {
				double relativeResult = 0.0;
				Double classifierTopTwoAverage = classifierTopHitFactorsAveragesMap.get(card.getClassifier());

				if (classifierTopTwoAverage != null && classifierTopTwoAverage > 0) {
					relativeResult = card.getHitFactor() / classifierTopTwoAverage;
				}
				competitorRelativeResults.add(relativeResult);
			}

			Collections.sort(competitorRelativeResults);
			Collections.reverse(competitorRelativeResults);
			if (competitorRelativeResults.size() > 4) {
				competitorRelativeResults = competitorRelativeResults.subList(0, 4);
			}

			double sum = 0.0;
			double average = 0.0;
			for (Double result : competitorRelativeResults) {
				if (comp.getLastName().equals("Virta")) Log.d("TEST", "JARNON result " + result);
				sum += result;
			}
			if (competitorRelativeResults.size() > 0) average = sum / competitorRelativeResults.size();

			competitorRelativeResultsAverages.put(comp, average);

		}

		DivisionRanking divisionRanking = new DivisionRanking();
		divisionRanking.setRows(generateRows(competitorRelativeResultsAverages));

		int rank = 1;
		for (DivisionRankingRow row : divisionRanking.getRows()) {
			Log.d("TEST", "ROW: " + rank++ + ". " + row.getCompetitor().getLastName() + " " + row.getBestResultsAverage());
		}
		return divisionRanking;

	}

	private List<DivisionRankingRow> generateRows(Map<Competitor, Double> competitorAveragesMap) {
		List<DivisionRankingRow> rows = new ArrayList<>();
		for (Competitor comp : competitorAveragesMap.keySet()) {
			rows.add(new DivisionRankingRow(comp, competitorAveragesMap.get(comp)));
		}
		Collections.sort(rows);
		Collections.reverse(rows);
		return rows;
	}

	@Override
	protected void onPostExecute(Ranking ranking) {

		if (handler != null) handler.process(ranking);
	}
}
