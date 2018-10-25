package haur.haurrankingandroid.service.task;

import android.os.AsyncTask;

import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.LoadStatisticsPostExecuteHandler;

/**
 * Created by Jarno on 20.10.2018.
 */

public class LoadStatisticsTask extends AsyncTask<Void, Void, Integer[]> {
	LoadStatisticsPostExecuteHandler handler;

	public LoadStatisticsTask(LoadStatisticsPostExecuteHandler handler) {
		this.handler = handler;
	}
	@Override
	protected Integer[] doInBackground(Void... voids) {
		AppDatabase db = AppDatabase.getDatabase();
		int competitionsCount = db.matchDao().getCount();
		int competitorsCount = db.competitorDao().getCount();
		return new Integer[] { competitionsCount, competitorsCount};
	}

	@Override
	protected void onPostExecute(Integer[] dbCounts) {
		int competitionsCount = dbCounts[0];
		int competitorsCount = dbCounts[1];
		handler.processStatistics(competitionsCount, competitorsCount);

	}
}
