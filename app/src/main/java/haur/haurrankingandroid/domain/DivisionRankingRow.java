package haur.haurrankingandroid.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Jarno on 14.10.2018.
 */

@Entity(foreignKeys = @ForeignKey(entity = DivisionRanking.class,
		parentColumns = "id",
		childColumns = "divisionRankingId",
		onDelete = CASCADE))
public class DivisionRankingRow implements Comparable<DivisionRankingRow> {
	@PrimaryKey(autoGenerate = true)
	private Long id;

	private Long divisionRankingId;

	private int rank;

	@Ignore
	private Competitor competitor;

	private Long competitorId;

	@Ignore
	private Double resultPercentage;

	@Ignore
	private Double bestResultsAverage;

	@Ignore
	private Double hitFactorAverage;

	private boolean rankedCompetitor;

	@Ignore
	private int resultsCount;

	@Ignore
	private Integer previousRank;

	@Ignore
	private boolean improvedResult = false;

	public DivisionRankingRow() {}

	public DivisionRankingRow(Competitor competitor, Double bestResultsAverage,
	                          Double hitFactorAverage, int resultsCount) {
		this.competitor = competitor;
		this.bestResultsAverage = bestResultsAverage;
		if (bestResultsAverage == null) {
			rankedCompetitor = false;
		}
		else rankedCompetitor = true;
		this.hitFactorAverage = hitFactorAverage;
		this.resultsCount = resultsCount;
	}
	@Override
	public int compareTo(DivisionRankingRow other) {

		if (this.bestResultsAverage == null) {
			if (other.getBestResultsAverage() == null) return compareByNames(other);
			else return -1;
		}
		if (other.bestResultsAverage == null) return 1;

		/* For Ascending order */
			if (this.bestResultsAverage < other.getBestResultsAverage())
				return -1;
			if (this.bestResultsAverage > other.getBestResultsAverage())
				return 1;

		return compareByNames(other);

	}

	private int compareByNames(DivisionRankingRow other) {
		int lastNameResult = other.getCompetitor().getLastName().compareTo(this.getCompetitor().getLastName());
		if (lastNameResult != 0)
			return lastNameResult;
		return other.getCompetitor().getFirstName().compareTo(this.getCompetitor().getLastName());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDivisionRankingId() {
		return divisionRankingId;
	}

	public void setDivisionRankingId(Long divisionRankingId) {
		this.divisionRankingId = divisionRankingId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public Long getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(Long competitorId) {
		this.competitorId = competitorId;
	}

	public Double getResultPercentage() {
		return resultPercentage;
	}

	public void setResultPercentage(Double resultPercentage) {
		this.resultPercentage = resultPercentage;
	}

	public Double getBestResultsAverage() {
		return bestResultsAverage;
	}

	public void setBestResultsAverage(Double bestResultsAverage) {
		this.bestResultsAverage = bestResultsAverage;
	}

	public Double getHitFactorAverage() {
		return hitFactorAverage;
	}

	public void setHitFactorAverage(Double hitFactorAverage) {
		this.hitFactorAverage = hitFactorAverage;
	}

	public boolean isRankedCompetitor() {
		return rankedCompetitor;
	}

	public void setRankedCompetitor(boolean rankedCompetitor) {
		this.rankedCompetitor = rankedCompetitor;
	}

	public int getResultsCount() {
		return resultsCount;
	}

	public void setResultsCount(int resultsCount) {
		this.resultsCount = resultsCount;
	}

	public Integer getPreviousRank() {
		return previousRank;
	}

	public void setPreviousRank(Integer previousRank) {
		this.previousRank = previousRank;
	}

	public boolean isImprovedResult() {
		return improvedResult;
	}

	public void setImprovedResult(boolean improvedResult) {
		this.improvedResult = improvedResult;
	}
}
