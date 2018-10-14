package haur.haurrankingandroid.service.ranking;

import android.util.Log;

import java.util.List;
import java.util.Map;

import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Division;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.service.persistence.SaveMatchTask;
import haur.haurrankingandroid.service.persistence.SaveMatchTaskResponseHandler;
import haur.haurrankingandroid.service.test.TestDataGenerator;

/**
 * Created by Jarno on 14.10.2018.
 */

public class RankingService {

	public static DivisionRanking saveTestData() {

//		TEST

//		Log.d("TEST", "\n Generating ranking data");
//		List<Match> matches = TestDataGenerator.generateTestData();
//		Log.d("TEST", "\n *** Starting save data");
//		new SaveMatchTask(new SaveMatchesResponseHandler()).execute(matches.toArray(new Match[] { }));
		new GenerateRankingTask(new GenerateRankingResnponseHandler()).execute();
		return null;
	}

	private static class SaveMatchesResponseHandler implements SaveMatchTaskResponseHandler {
		@Override
		public void process() {
			Log.d("TEST", "\n PROCESS METHOD: TEST DATA GENERATION FINISHED" +
					"\nCALLING GENERATE RANKING...");
			new GenerateRankingTask(new GenerateRankingResnponseHandler()).execute();
		}
	}

	private static class GenerateRankingResnponseHandler implements GenerateRankingResponseHandler {
		@Override
		public void process(Ranking ranking) {
			Log.d("RANKING HANDLER", "\n *** Got ranking result");
		}
	}


	// Returns a Map of average of top two hitfactors for each valid classifier (min 2 results)
	public Map<Classifier, Double> getClassifierBestResultsAverages(Division division) {
		return null;
	}
}
