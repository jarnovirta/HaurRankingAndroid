package haur.haurrankingandroid.service.ranking;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.service.task.GenerateRankingTask;
import haur.haurrankingandroid.service.task.SaveExportedRankingTask;

/**
 * Created by Jarno on 14.10.2018
 */

public class RankingService {

	private static MutableLiveData<Ranking> ranking;

	public static LiveData<Ranking> getRanking() {
		if (ranking == null) {
			ranking = new MutableLiveData<>();
			generateRanking();
		}
		return ranking;
	}

	public static void generateRanking() {
		new GenerateRankingTask(newRanking ->
					ranking.postValue(newRanking)
			).execute();
	}

	public static void saveExportedRanking(Ranking ranking) {
		new SaveExportedRankingTask().execute(ranking);
	}
}
