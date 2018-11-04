package haur.haurrankingandroid.service.task;

import android.os.AsyncTask;

import java.util.List;

import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.service.ranking.RankingService;

/**
 * Created by Jarno on 24.10.2018.
 */

public class DeleteMatchesTask extends AsyncTask<Void, Void, Void> {

	private List<Long> ids;

	public DeleteMatchesTask(List<Long> ids) {
		this.ids = ids;
	}
	@Override
	protected Void doInBackground(Void... voids) {
		AppDatabase db = AppDatabase.getDatabase();

		for (Long id : ids) {
			db.scoreCardDao().deleteByMatch(id);
			db.matchDao().delete(id);
		}
		db.rankingDao().setRankingDataChanged(true);
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		RankingService.generateRanking();
	}
}
