package haur.haurrankingandroid.service;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Map;

import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Division;
import haur.haurrankingandroid.domain.Ranking;

/**
 * Created by Jarno on 14.10.2018.
 */

public class RankingService {

	public static Ranking getRanking() {
		List<AsyncTask> tasks = TestDataGenerator.generateTestData();
		Log.d("TEST", "First task finished " + (tasks.get(0).getStatus()));
		Log.d("TEST", "TEST DATA GENERATION FINISHED");
		return null;
	}

		// Loop divisions

			// Get classifiers with min 2 results and average of top two hitfactors

			// Get competitors with min 4 results in division for valid classifiers. Get max 8
			// latest result cards, order by match date.

			// Loop competitors
				// and calculate relative classifier results. Get average of best 4.
				// Get RankingRow for competitor with average result.

			// Order rows by score average.

			// Set percentages etc.
		//


	// Returns a Map of average of top two hitfactors for each valid classifier (min 2 results)
	public Map<Classifier, Double> getClassifierBestResultsAverages(Division division) {
		return null;
	}
}
