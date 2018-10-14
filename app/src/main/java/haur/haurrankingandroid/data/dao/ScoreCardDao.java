package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import haur.haurrankingandroid.domain.ScoreCard;

/**
 * Created by Jarno on 13.10.2018.
 */
@Dao
public interface ScoreCardDao {
	@Insert
	void insertAll(List<ScoreCard> scoreCards);

	@Query("SELECT * FROM ScoreCard")
	List<ScoreCard> findAll();


}
