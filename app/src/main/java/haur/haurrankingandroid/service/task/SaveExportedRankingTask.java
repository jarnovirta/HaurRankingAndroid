package haur.haurrankingandroid.service.task;

import android.os.AsyncTask;
import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.domain.RankingDataChangedEntity;

/**
 * Created by Jarno on 3.11.2018.
 */

public class SaveExportedRankingTask extends AsyncTask<Ranking, Void, Void> {
	@Override
	protected Void doInBackground(Ranking... rankings) {
		AppDatabase db = AppDatabase.getDatabase();
		RankingDataChangedEntity entity = db.rankingDao().getRankingDataChanged();
		if (entity == null || entity.isDataChanged()) {
			for (Ranking ranking : rankings) {
				db.rankingDao().saveRanking(ranking);
			}
		}
		db.rankingDao().setRankingDataChanged(false);
		return null;
	}
}
