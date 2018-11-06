package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Division;
import haur.haurrankingandroid.domain.ScoreCard;

/**
 * Created by Jarno on 13.10.2018.
 */
@Dao
public abstract class ScoreCardDao {

	public void insertAll(List<ScoreCard> scoreCards, Date matchDate) {
		// Remove older results before inserting card
		for (ScoreCard card :scoreCards) {
			deleteOldScoreCards(card.getCompetitorId(), card.getClassifier(),
					card.getDivision(), matchDate);
			List<ScoreCard> existingCards = findExistingScoreCards(card.getCompetitorId(),
					card.getClassifier(), card.getDivision());
			if (existingCards.size() == 0) {
				insert(card);
			}
		}
	}

	@Insert
	abstract Long insert(ScoreCard card);

	@Query("SELECT * FROM scorecard WHERE competitorId = :competitorId " +
			"AND classifier = :classifier " +
			"AND division = :division")
	abstract List<ScoreCard> findExistingScoreCards(Long competitorId, Classifier classifier,
	                                                 Division division);

	// Find ScoreCards for Classifiers with min. 2 results in Division.
	@Query("SELECT classifier FROM (SELECT classifier, COUNT(id) AS id_count " +
			"FROM ScoreCard " +
			"WHERE division = :division " +
			"GROUP BY classifier) " +
			"WHERE id_count >= 2")
	public abstract List<Classifier> getValidClassifiers(Division division);

	@Query("SELECT AVG(hitFactor) FROM (SELECT hitFactor FROM ScoreCard " +
			"WHERE classifier = :classifier and division = :division " +
			"ORDER BY hitFactor DESC LIMIT 2)"  )
	public abstract Double getTopTwoHitFactorsAverage(Division division, Classifier classifier);

	@Query("SELECT * FROM ScoreCard sc " +
			"INNER JOIN ipscmatch m ON sc.matchId = m.id " +
			"WHERE sc.division = :division " +
			"AND sc.competitorId = :competitorId " +
			"AND sc.classifier IN (:classifiers)" +
			"ORDER BY m.date DESC " +
			"LIMIT 8")
	public abstract List<ScoreCard> getForRanking(Division division, Long competitorId,
	                                              List<Classifier> classifiers);

	@Query("SELECT AVG(hf) FROM (SELECT hitfactor AS hf FROM ScoreCard sc "
			+ "INNER JOIN ipscmatch m ON sc.matchId = m.id "
			+ "WHERE sc.competitorId = :competitorId "
			+ "AND sc.division = :division "
			+ "AND sc.classifier IN (:validClassifiers) "
			+ "ORDER BY m.date DESC LIMIT 8)")
	public abstract Double getCompetitorLatestHfAverage(Long competitorId, Division division,
	                                                    List<Classifier> validClassifiers);

	@Query("SELECT COUNT(id) FROM ScoreCard WHERE competitorId = :competitorId " +
			"AND division = :division " +
			"AND classifier IN (:validClassifiers)")
	public abstract int getCountByCompetitor(Long competitorId, Division division,
	                                         List<Classifier> validClassifiers);

	@Query("SELECT DISTINCT(division) FROM ScoreCard")
	public abstract List<Division> findAllDivisions();

	@Query("DELETE FROM scorecard WHERE matchId = :matchId")
	public abstract void deleteByMatch(Long matchId);

	@Query("DELETE FROM scorecard WHERE competitorId = :competitorId")
	public abstract void deleteByCompetitor(Long competitorId);

	@Query("DELETE FROM scorecard WHERE id " +
			"IN (SELECT sc.id FROM scorecard sc " +
			"INNER JOIN ipscmatch m ON sc.matchId = m.id " +
			"WHERE sc.competitorId = :competitorId " +
			"AND sc.division = :division " +
			"AND sc.classifier = :classifier " +
			"AND m.date < :olderThanDate)")
	public abstract void deleteOldScoreCards(Long competitorId, Classifier classifier,
	                                         Division division, Date olderThanDate);
}