package haur.haurrankingandroid.service.ranking;

import android.util.Log;

import java.util.List;

import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.service.task.SaveMatchTask;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.SaveMatchTaskResponseHandler;
import haur.haurrankingandroid.service.test.TestDataGenerator;

/**
 * Created by Jarno on 14.10.2018.
 */

public class RankingService {

	public static DivisionRanking saveTestData() {

//		TEST

		Log.d("TEST", "\n Generating ranking data");
		List<Match> matches = TestDataGenerator.generateTestData();

		new SaveMatchTask(new SaveMatchesResponseHandler()).execute(matches.toArray(new Match[] { }));

		return null;
	}

	private static class SaveMatchesResponseHandler implements SaveMatchTaskResponseHandler {
		@Override
		public void process() {
			Log.d("TEST", "\n PROCESS METHOD: TEST DATA GENERATION FINISHED" +
					"\nCALLING GENERATE RANKING...");
		}
	}
}
