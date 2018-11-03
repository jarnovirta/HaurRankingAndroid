package haur.haurrankingandroid.service.task;

import android.os.AsyncTask;

import java.util.List;

import haur.haurrankingandroid.data.AppDatabase;

/**
 * Created by Jarno on 25.10.2018.
 */

public class DeleteCompetitorsTask extends AsyncTask<Void, Void, Void> {
	private List<Long> ids;

	public DeleteCompetitorsTask(List<Long> ids) {
		this.ids = ids;
	}
	@Override
	protected Void doInBackground(Void... voids) {
		AppDatabase db = AppDatabase.getDatabase();

		for (Long id : ids) {
			db.scoreCardDao().deleteByCompetitor(id);
			db.competitorDao().delete(id);
		}
		return null;
	}

}