package haur.haurrankingandroid.service.task;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import haur.haurrankingandroid.activity.fragment.MatchesTabFragment;
import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.MatchListItem;

/**
 * Created by Jarno on 20.10.2018.
 */

public class LoadMatchListTask extends AsyncTask<Void, Void, List<MatchListItem>> {
	private MatchesTabFragment fragment;

	public LoadMatchListTask(MatchesTabFragment fragment) {
		this.fragment = fragment;
	}
	@Override
	protected List<MatchListItem> doInBackground(Void... voids) {
		AppDatabase db = AppDatabase.getDatabase();
		List<Match> matches = db.matchDao().findAll();

		List<MatchListItem> items = new ArrayList<>();
		for (Match match : matches) {
			List<Classifier> classifiers = db.matchDao().getClassifiersForMatch(match.getId());
			items.add(new MatchListItem(match, classifiers));
		}
		return items;
	}

	@Override
	protected void onPostExecute(List<MatchListItem> matchListItems) {
		fragment.setMatchList(matchListItems);
		new LoadMatchListTask(fragment);
	}
}
