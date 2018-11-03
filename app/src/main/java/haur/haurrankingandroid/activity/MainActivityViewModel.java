package haur.haurrankingandroid.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.service.task.GenerateRankingTask;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.GenerateRankingPostExecuteHandler;

/**
 * Created by Jarno on 28.10.2018.
 */

public class MainActivityViewModel extends ViewModel {
	private MutableLiveData<Ranking> ranking;

	public LiveData<Ranking> getRanking() {
		if (ranking == null) {
			ranking = new MutableLiveData<Ranking>();
			new GenerateRankingTask(new GenerateRankingPostExecuteHandler() {
				@Override
				public void process(Ranking newRanking) {
					ranking.setValue(newRanking);
				}
			});
		}
		return ranking;
	}

	public void updateRanking(Ranking newRanking) {
		ranking.setValue(newRanking);
	}
}
