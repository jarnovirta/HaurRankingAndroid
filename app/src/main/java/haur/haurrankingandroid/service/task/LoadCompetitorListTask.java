package haur.haurrankingandroid.service.task;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.CompetitorListItem;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.LoadCompetitorListHandler;

/**
 * Created by Jarno on 20.10.2018.
 */

public class LoadCompetitorListTask extends AsyncTask<Void, Void, List<CompetitorListItem>> {

	private LoadCompetitorListHandler handler;

	public LoadCompetitorListTask(LoadCompetitorListHandler handler) {
		this.handler = handler;
	}

	@Override
	protected List<CompetitorListItem> doInBackground(Void... voids) {
		AppDatabase db = AppDatabase.getDatabase();
		List<CompetitorListItem> resultList = new ArrayList<>();
		List<Competitor> comps = db.competitorDao().findAll();
		for (Competitor comp : comps) {
			resultList.add(new CompetitorListItem(comp));
		}
		return resultList;
	}

	@Override
	protected void onPostExecute(List<CompetitorListItem> newCompetitorList) {
		handler.process(newCompetitorList);
	}
}
