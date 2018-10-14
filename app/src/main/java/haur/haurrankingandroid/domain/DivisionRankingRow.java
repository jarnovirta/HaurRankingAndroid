package haur.haurrankingandroid.domain;

/**
 * Created by Jarno on 14.10.2018.
 */

public class DivisionRankingRow implements Comparable<DivisionRankingRow> {
	private Competitor competitor;
	private double resultPercentage;
	private double bestResultsAverage;
	private double hitFactorAverage;
	private boolean rankedCompetitor;
	private int resultsCount;

	private Integer previousRank;

	private boolean improvedResult = false;

	public DivisionRankingRow(Competitor competitor, double bestResultsAverage) {
		this.competitor = competitor;
		this.bestResultsAverage = bestResultsAverage;
		this.rankedCompetitor = rankedCompetitor;
	}
	@Override
	public int compareTo(DivisionRankingRow other) {
		double compareToAverage = other.getBestResultsAverage();
		/* For Ascending order */
		if (this.bestResultsAverage < compareToAverage)
			return -1;
		if (this.bestResultsAverage > compareToAverage)
			return 1;
		if (competitor == null || other == null || other.getCompetitor() == null)
			return 0;
		int lastNameResult = other.getCompetitor().getLastName().compareTo(this.getCompetitor().getLastName());
		if (lastNameResult != 0)
			return lastNameResult;
		return other.getCompetitor().getFirstName().compareTo(this.getCompetitor().getLastName());

	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public double getResultPercentage() {
		return resultPercentage;
	}

	public void setResultPercentage(double resultPercentage) {
		this.resultPercentage = resultPercentage;
	}

	public double getBestResultsAverage() {
		return bestResultsAverage;
	}

	public void setBestResultsAverage(double bestResultsAverage) {
		this.bestResultsAverage = bestResultsAverage;
	}

	public double getHitFactorAverage() {
		return hitFactorAverage;
	}

	public void setHitFactorAverage(double hitFactorAverage) {
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
