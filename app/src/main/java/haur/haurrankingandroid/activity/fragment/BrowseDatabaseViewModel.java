package haur.haurrankingandroid.activity.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import haur.haurrankingandroid.domain.CompetitorListItem;
import haur.haurrankingandroid.domain.MatchListItem;
import haur.haurrankingandroid.service.task.LoadCompetitorListTask;
import haur.haurrankingandroid.service.task.LoadMatchListTask;

/**
 * Created by Jarno on 28.10.2018.
 */

public class BrowseDatabaseViewModel extends ViewModel {
	private MutableLiveData<List<CompetitorListItem>> competitorListItems;
	private MutableLiveData<List<MatchListItem>> matchListItems;

	public MutableLiveData<List<CompetitorListItem>> getCompetitorListItems() {
		if (competitorListItems == null) {
			competitorListItems = new MutableLiveData<>();
			update();
		}
		return competitorListItems;
	}

	public LiveData<List<MatchListItem>> getMatchListItems() {
		if (matchListItems == null) {
			matchListItems = new MutableLiveData<>();
			update();
		}
		return matchListItems;
	}

	public void update() {
		new LoadCompetitorListTask(newCompetitorListItems -> {
			competitorListItems.setValue(newCompetitorListItems);
		}).execute();
		new LoadMatchListTask(newMatchListItems -> {
			matchListItems.setValue(newMatchListItems);
		}).execute();

	}
}
