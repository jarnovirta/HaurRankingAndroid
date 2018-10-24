package haur.haurrankingandroid.service.task;

import android.os.AsyncTask;

import java.util.List;

import haur.haurrankingandroid.activity.fragment.MatchesTabFragment;
import haur.haurrankingandroid.data.AppDatabase;

/**
 * Created by Jarno on 24.10.2018.
 */

public class DeleteMatchesTask extends AsyncTask<Void, Void, Void> {

	List<Long> ids;
	MatchesTabFragment fragment;

	public DeleteMatchesTask(List<Long> ids, MatchesTabFragment fragment) {
		this.ids = ids;
		this.fragment = fragment;
	}
	@Override
	protected Void doInBackground(Void... voids) {
		AppDatabase db = AppDatabase.getDatabase();

		for (Long id : ids) {
			db.scoreCardDao().deleteByMatch(id);
			db.matchDao().delete(id);
		}
		return null;
	}
}
