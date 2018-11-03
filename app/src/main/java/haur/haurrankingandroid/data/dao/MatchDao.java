package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import haur.haurrankingandroid.data.dao.TypeConverters.ClassifierConverter;
import haur.haurrankingandroid.data.dao.TypeConverters.DateConverter;
import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Match;

/**
 * Created by Jarno on 13.10.2018.
 */

@Dao
public interface MatchDao {
	@Insert
	Long insert(Match match);

	@Query("SELECT * FROM ipscmatch WHERE id IN (:ids)")
	List<Match> find(List<Long> ids);

	@Query("SELECT * FROM ipscmatch")
	List<Match> findAll();

	@Query("SELECT COUNT(id) FROM ipscmatch")
	int getCount();

	@TypeConverters( {ClassifierConverter.class })
	@Query("SELECT DISTINCT(sc.classifier) FROM scorecard sc " +
			"INNER JOIN ipscmatch m ON sc.matchId = m.id " +
			"WHERE m.id = :id")
	List<Classifier> getClassifiersForMatch(Long id);

	@Query("DELETE FROM ipscmatch WHERE id = :id")
	void delete(Long id);

	@TypeConverters(DateConverter.class)
	@Query("SELECT * FROM ipscmatch WHERE name = :name AND date = :date")
	Match findByNameAndDate(String name, Date date);
}
