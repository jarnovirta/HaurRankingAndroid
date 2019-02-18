package haur.haurrankingandroid.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Environment;

import haur.haurrankingandroid.RankingAppContext;
import haur.haurrankingandroid.data.dao.CompetitorDao;
import haur.haurrankingandroid.data.dao.MatchDao;
import haur.haurrankingandroid.data.dao.RankingDao;
import haur.haurrankingandroid.data.dao.ScoreCardDao;
import haur.haurrankingandroid.data.dao.TypeConverters.DomainTypeConverters;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.DivisionRankingRow;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.domain.RankingDataChangedEntity;
import haur.haurrankingandroid.domain.ScoreCard;

/**
 * Created by Jarno on 13.10.2018
 */

@TypeConverters({DomainTypeConverters.class})
@Database(entities = { Match.class, ScoreCard.class, Competitor.class, Ranking.class,
		DivisionRanking.class, DivisionRankingRow.class, RankingDataChangedEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

	private static AppDatabase database;
	private static String externalStorageFolder = Environment.getExternalStorageDirectory().getAbsolutePath();
	private static final String DATABASE_NAME = externalStorageFolder + "/HaurRanking/data/haur_ranking_db";

	public abstract MatchDao matchDao();

	public abstract ScoreCardDao scoreCardDao();

	public abstract CompetitorDao competitorDao();

	public abstract RankingDao rankingDao();

	public static AppDatabase getDatabase() throws SQLiteCantOpenDatabaseException {
		if (database == null) {
			database = Room.databaseBuilder(RankingAppContext.getAppContext(),
						AppDatabase.class, DATABASE_NAME).build();
		}
		return database;
	}

}
