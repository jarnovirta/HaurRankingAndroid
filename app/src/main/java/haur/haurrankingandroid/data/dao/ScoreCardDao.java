package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import haur.haurrankingandroid.data.dao.TypeConverters.DivisionConverter;
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
	@TypeConverters({ DivisionConverter.class })
	@Query("SELECT classifier FROM (SELECT classifier, COUNT(id) AS id_count " +
			"FROM ScoreCard " +
			"WHERE division = :division " +
			"GROUP BY classifier) " +
			"WHERE id_count >= 2")
	List<String> getValidClassifiers(Division division);

	@TypeConverters({ DivisionConverter.class })
	@Query("SELECT AVG(hitFactor) FROM (SELECT hitFactor FROM ScoreCard " +
			"WHERE classifier = :classifier and division = :division " +
			"ORDER BY hitFactor DESC LIMIT 2)"  )
	Double getTopTwoHitFactorsAverage(Division division, String classifier);

	@TypeConverters({ DivisionConverter.class })
	@Query("SELECT * FROM ScoreCard sc " +
			"INNER JOIN ipscmatch m ON sc.matchId = m.id " +
			"WHERE sc.division = :division " +
			"AND sc.competitorId = :competitorId " +
			"AND sc.classifier IN (:classifiers)" +
			"ORDER BY m.date DESC " +
			"LIMIT 8")
	List<ScoreCard> getForRanking(Division division, Long competitorId, List<String> classifiers);

	@TypeConverters({ DivisionConverter.class })
	@Query("SELECT AVG(hf) FROM (SELECT hitfactor AS hf FROM ScoreCard sc "
			+ "INNER JOIN ipscmatch m ON sc.matchId = m.id "
			+ "WHERE sc.competitorId = :competitorId "
			+ "AND sc.division = :division "
			+ "AND sc.classifier IN (:validClassifiers) "
			+ "ORDER BY m.date DESC LIMIT 8)")
	Double getCompetitorLatestHfAverage(Long competitorId, Division division, List<String> validClassifiers);

	@TypeConverters({ DivisionConverter.class })
	@Query("SELECT COUNT(id) FROM ScoreCard WHERE competitorId = :competitorId " +
			"AND division = :division")
	int getCountByCompetitor(Long competitorId, Division division);
}