package haur.haurrankingandroid.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.os.Environment;

import java.util.List;

import haur.haurrankingandroid.RankingAppContext;
import haur.haurrankingandroid.data.dao.CompetitorDao;
import haur.haurrankingandroid.data.dao.MatchDao;
import haur.haurrankingandroid.data.dao.ScoreCardDao;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.ScoreCard;

/**
 * Created by Jarno on 13.10.2018.
 */

@Database(entities = { Match.class, ScoreCard.class, Competitor.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

	static AppDatabase database;

	static String externalStorageFolder = Environment.getExternalStorageDirectory().getAbsolutePath();
	static final String DATABASE_NAME = externalStorageFolder + "/HaurRanking/data/haur_ranking_db";

	public abstract MatchDao matchDao();

	public abstract ScoreCardDao scoreCardDao();

	public abstract CompetitorDao competitorDao();

	public static AppDatabase getDatabase() {

		if (database == null) {
			database = Room.databaseBuilder(RankingAppContext.getAppContext(), AppDatabase.class, DATABASE_NAME).build();
		}

		return database;
	}

}
