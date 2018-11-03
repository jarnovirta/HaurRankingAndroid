package haur.haurrankingandroid.service.ranking;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.service.task.GenerateRankingTask;
import haur.haurrankingandroid.service.task.SaveMatchTask;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.GenerateRankingPostExecuteHandler;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.SaveMatchTaskResponseHandler;
import haur.haurrankingandroid.service.test.TestDataGenerator;

/**
 * Created by Jarno on 14.10.2018.
 */

public class RankingService {

	private static MutableLiveData<Ranking> ranking;
	public static void saveTestData() {

//		TEST

		Log.d("TEST", "\n Generating ranking data");
		List<Match> matches = TestDataGenerator.generateTestData();

//		new SaveMatchTask(new SaveMatchesResponseHandler()).execute(matches.toArray(new Match[] { }));

	}

	private static class SaveMatchesResponseHandler implements SaveMatchTaskResponseHandler {
		@Override
		public void process() {
			Log.d("TEST", "\n PROCESS METHOD: TEST DATA GENERATION FINISHED" +
					"\nCALLING GENERATE RANKING...");
		}
	}

	public static LiveData<Ranking> getRanking() {
		if (ranking == null) {
			ranking = new MutableLiveData<Ranking>();
			generateRanking();
		}
		return ranking;
	}

	public static void generateRanking() {

		new GenerateRankingTask(newRanking -> {
				Log.i("TEST", "SETTING NEW RANKING VALUE");
				ranking.postValue(newRanking);
		}).execute();
	}
}
