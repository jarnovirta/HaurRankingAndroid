package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import haur.haurrankingandroid.domain.Match;

/**
 * Created by Jarno on 13.10.2018.
 */

@Dao
public interface MatchDao {
	@Insert
	Long insert(Match match);

	@Query("SELECT * FROM Match WHERE id IN (:ids)")
	List<Match> find(List<Long> ids);
}
