package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import haur.haurrankingandroid.data.dao.TypeConverters.ClassifierConverter;
import haur.haurrankingandroid.data.dao.TypeConverters.DateConverter;
import haur.haurrankingandroid.data.dao.TypeConverters.DivisionConverter;
import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Division;
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

	// Find ScoreCards for Classifiers with min. 2 results in Division.
	@TypeConverters({ DivisionConverter.class, ClassifierConverter.class })
	@Query("SELECT * FROM scorecard " +
			"WHERE division = :division " +
			"AND classifier IN (" +
			"SELECT classifier FROM (SELECT classifier, COUNT(id) AS id_count " +
			"FROM ScoreCard " +
			"WHERE division = :division " +
			"GROUP BY classifier) " +
			"WHERE id_count >= 2)")
	List<ScoreCard> findForValidClassifiers(Division division);
}