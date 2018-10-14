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
import haur.haurrankingandroid.domain.Competitor;
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
	@Query("SELECT classifier FROM (SELECT classifier, COUNT(id) AS id_count " +
			"FROM ScoreCard " +
			"WHERE division = :division " +
			"GROUP BY classifier) " +
			"WHERE id_count >= 2")
	List<Classifier> getValidClassifiers(Division division);

	@TypeConverters({ DivisionConverter.class, ClassifierConverter.class })
	@Query("SELECT AVG(hitFactor) FROM (SELECT hitFactor FROM ScoreCard " +
			"WHERE classifier = :classifier and division = :division " +
			"ORDER BY hitFactor DESC LIMIT 2)"  )
	Double getTopTwoHitFactorsAverage(Division division, Classifier classifier);

	@TypeConverters({ DivisionConverter.class, ClassifierConverter.class })
	@Query("SELECT * FROM ScoreCard sc " +
			"INNER JOIN ipscmatch m ON sc.matchId = m.id " +
			"WHERE sc.division = :division " +
			"AND sc.competitorId = :competitorId " +
			"AND sc.classifier IN (:classifiers)" +
			"ORDER BY m.date DESC " +
			"LIMIT 8")
	List<ScoreCard> getForRanking(Division division, Long competitorId, List<Classifier> classifiers);
}