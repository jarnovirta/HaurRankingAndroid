package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;
import java.util.Set;

import haur.haurrankingandroid.data.dao.TypeConverters.DivisionConverter;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Division;

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

	@TypeConverters({ DivisionConverter.class })
	@Query("SELECT * FROM competitor WHERE id IN " +
			"(SELECT competitor_id FROM " +
			"(SELECT c.id as competitor_id, count(sc.id) as scorecard_count " +
			"FROM competitor c " +
			"LEFT JOIN scorecard sc ON sc.competitorId = c.id " +
			"WHERE sc.division = :division AND sc.classifier IN (:classifiers) " +
			"GROUP BY c.id) " +
			"WHERE scorecard_count > 0)")
	List<Competitor> getCompetitorsWithResults(Division division, List<String> classifiers);

	@Query("SELECT COUNT(id) FROM competitor")
	int getCount();

	@Query("SELECT * FROM competitor")
	List<Competitor> findAll();
}
