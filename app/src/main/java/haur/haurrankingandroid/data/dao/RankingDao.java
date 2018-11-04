package haur.haurrankingandroid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.DivisionRankingRow;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.domain.RankingDataChangedEntity;

/**
 * Created by Jarno on 3.11.2018.
 */

@Dao
public abstract class RankingDao {
	public Ranking getRanking() {
		Ranking ranking = getRankingEntity();
		if (ranking != null) {
			ranking.setDivisionRankings(getDivisionRankings(ranking.getId()));
			for (DivisionRanking divRanking : ranking.getDivisionRankings()) {
				divRanking.setRows(getDivisionRankingRows(divRanking.getId()));
			}
		}
		return ranking;
	}

	@Transaction
	public void saveRanking(Ranking ranking) {
		Long rankingId = insertRanking(ranking);
		for (DivisionRanking divisionRanking : ranking.getDivisionRankings()) {
			divisionRanking.setRankingId(rankingId);
			Long divRankingId = insertDivRanking(divisionRanking);
			for (DivisionRankingRow row : divisionRanking.getRows()) {
				row.setDivisionRankingId(divRankingId);
				row.setCompetitorId(row.getCompetitor().getId());
				row.setRank(divisionRanking.getRows().indexOf(row) + 1);
			}
			insertRows(divisionRanking.getRows());
		}
	}

	public void setRankingDataChanged(boolean changed) {
		deleteRankingDataChanged();
		insertRankingDataChanged(new RankingDataChangedEntity(changed));
	}
	@Query("DELETE FROM RankingDataChangedEntity")
	public abstract void deleteRankingDataChanged();

	@Insert
	public abstract void insertRankingDataChanged(RankingDataChangedEntity entity);

	@Query("SELECT * FROM RankingDataChangedEntity")
	public abstract RankingDataChangedEntity getRankingDataChanged();

	@Query("SELECT * FROM Ranking LIMIT 1")
	abstract Ranking getRankingEntity();

	@Query("SELECT * FROM DivisionRanking WHERE rankingId = :rankingId")
	public abstract List<DivisionRanking> getDivisionRankings(Long rankingId);

	@Query("SELECT * FROM DivisionRankingRow WHERE divisionRankingId = :divisionRankingId")
	public abstract List<DivisionRankingRow> getDivisionRankingRows(Long divisionRankingId);

	@Query("DELETE FROM Ranking")
	public abstract void delete();

	@Insert
	public abstract Long insertRanking(Ranking ranking);

	@Insert
	public abstract Long insertDivRanking(DivisionRanking divisionRanking);

	@Insert
	public abstract List<Long> insertRows(List<DivisionRankingRow> rows);
}
