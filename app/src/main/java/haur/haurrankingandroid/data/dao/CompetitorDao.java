package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
import java.util.Set;

import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.ScoreCard;

/**
 * Created by Jarno on 13.10.2018.
 */

@Dao
public interface CompetitorDao {
	@Insert
	Long insert(Competitor competitor);

	@Query("SELECT * FROM competitor WHERE id IN (:ids)")
	List<Competitor> find(Set<Long> ids);

	@Query("SELECT * FROM competitor WHERE firstName LIKE :firstName AND lastName LIKE :lastName")
	Competitor findByName(String firstName, String lastName);
}
