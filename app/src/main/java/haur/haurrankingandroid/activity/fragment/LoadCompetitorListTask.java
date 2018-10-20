package haur.haurrankingandroid.activity.fragment;

import android.os.AsyncTask;

import java.util.List;

import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Competitor;

/**
 * Created by Jarno on 20.10.2018.
 */

public class LoadCompetitorListTask extends AsyncTask<Void, Void, List<Competitor>> {

	private CompetitorsTabFragment fragment;

	public LoadCompetitorListTask(CompetitorsTabFragment fragment) {
		this.fragment = fragment;
	}

	@Override
	protected List<Competitor> doInBackground(Void... voids) {
		AppDatabase db = AppDatabase.getDatabase();
		return db.competitorDao().findAll();
	}

	@Override
	protected void onPostExecute(List<Competitor> newCompetitorList) {
		fragment.setCompetitors(newCompetitorList);
	}
}
