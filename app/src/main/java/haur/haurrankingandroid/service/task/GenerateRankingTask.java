package haur.haurrankingandroid.service.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Division;
import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.DivisionRankingRow;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.domain.ScoreCard;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.GenerateRankingPostExecuteHandler;

/**
 * Created by Jarno on 14.10.2018.
 */

public class GenerateRankingTask extends AsyncTask<Void, Void, Ranking> {

	private GenerateRankingPostExecuteHandler handler;

	public GenerateRankingTask(GenerateRankingPostExecuteHandler handler) {

		this.handler = handler;
	}
	@Override
	protected Ranking doInBackground(Void... args) {
		Ranking ranking = new Ranking();
		ranking.setDivisionRankings(new ArrayList<DivisionRanking>());

		for (Division division : Division.values()) {
			DivisionRanking divRank = generateDivisionRanking(division);
			if (divRank != null) ranking.getDivisionRankings().add(divRank);
		}
		return ranking;
	}

	private DivisionRanking generateDivisionRanking(Division division) {

		AppDatabase db = AppDatabase.getDatabase();

		// Get classifiers with min. 2 results
		List<Classifier> validClassifiers = db.scoreCardDao().getValidClassifiers(division);

		// Average of top two hitfactors for a classifier
		Map<Classifier, Double> classifierTopHitFactorsAveragesMap = new HashMap<>();

		for (Classifier classifier : validClassifiers) {
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
			Double relativeResultAverage = null;
			if (competitorRelativeResults.size() == 4) {
				double relativeResultSum = 0.0;
				for (Double result : competitorRelativeResults) {
					relativeResultSum += result;
				}
				relativeResultAverage = relativeResultSum / competitorRelativeResults.size();
			}
			competitorRelativeResultsAverages.put(comp, relativeResultAverage);
		}
		List<DivisionRankingRow> rows = generateRows(competitorRelativeResultsAverages, division,
				validClassifiers);
		if (rows.size() > 0) return new DivisionRanking(division,
				generateRows(competitorRelativeResultsAverages, division, validClassifiers));

		else return null;
	}


	private List<DivisionRankingRow> generateRows(Map<Competitor, Double> competitorResultAveragesMap,
	                                              Division division, List<Classifier> validClassifiers) {
		AppDatabase db = AppDatabase.getDatabase();
		List<DivisionRankingRow> rows = new ArrayList<>();
		for (Competitor comp : competitorResultAveragesMap.keySet()) {
			Double hfAverage = db.scoreCardDao().getCompetitorLatestHfAverage(comp.getId(), division,
					validClassifiers);
			int resultCount = db.scoreCardDao().getCountByCompetitor(comp.getId(), division);
			rows.add(new DivisionRankingRow(comp, competitorResultAveragesMap.get(comp),
					hfAverage, resultCount));
		}
		Collections.sort(rows);
		Collections.reverse(rows);
		if (rows.size() > 0) {
			Double bestResult = rows.get(0).getBestResultsAverage();
				for (DivisionRankingRow row : rows) {
					if (row.getBestResultsAverage() != null && bestResult != null) {
						row.setResultPercentage(row.getBestResultsAverage() / bestResult * 100);
					}
				}
		}
		return rows;
	}

	@Override
	protected void onPostExecute(Ranking ranking) {
		if (handler != null) {
			Log.d("TEST", "TASK POST EXECUTE");
			handler.process(ranking);
		}
	}
}