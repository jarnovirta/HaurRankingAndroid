package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import haur.haurrankingandroid.domain.Match;

/**
 * Created by Jarno on 13.10.2018.
 */

@Dao
public interface MatchDao {
	@Insert
	Long insert(Match match);
}
