package haur.haurrankingandroid.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jarno on 14.10.2018.
 */

public class Ranking {

	private List<DivisionRanking> divisionRankings = new ArrayList<DivisionRanking>();

	private Calendar date;

	private int totalResultsCount;
	private int competitorsWithRank;
	private int validClassifiersCount;

	private Calendar latestIncludedMatchDate;
	private String latestIncludedMatchName;

	public Ranking() {
		date = Calendar.getInstance();
	}

	public void setTotalCompetitorsAndResultsCounts() {
		totalResultsCount = 0;
		validClassifiersCount = 0;
		competitorsWithRank = 0;
		Set<Classifier> classifiers = new HashSet<Classifier>();
		Set<Competitor> competitors = new HashSet<Competitor>();
		for (DivisionRanking divisionRanking : divisionRankings) {
			for (Classifier classifier : divisionRanking.getValidClassifiers()) {
				if (!classifiers.contains(classifier))
					classifiers.add(classifier);
			}
			for (DivisionRankingRow line : divisionRanking.getRows()) {
				totalResultsCount += line.getResultsCount();
				if (line.isRankedCompetitor() && !competitors.contains(line.getCompetitor()))
					competitors.add(line.getCompetitor());
			}
		}
		validClassifiersCount = classifiers.size();
		competitorsWithRank = competitors.size();
	}

	public List<DivisionRanking> getDivisionRankings() {
		return divisionRankings;
	}

	public void setDivisionRankings(List<DivisionRanking> divisionRankings) {
		this.divisionRankings = divisionRankings;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getTotalResultsCount() {
		return totalResultsCount;
	}

	public void setTotalResultsCount(int totalResultsCount) {
		this.totalResultsCount = totalResultsCount;
	}

	public int getCompetitorsWithRank() {
		return competitorsWithRank;
	}

	public void setCompetitorsWithRank(int competitorsWithRank) {
		this.competitorsWithRank = competitorsWithRank;
	}

	public int getValidClassifiersCount() {
		return validClassifiersCount;
	}

	public void setValidClassifiersCount(int validClassifiersCount) {
		this.validClassifiersCount = validClassifiersCount;
	}

	public Calendar getLatestIncludedMatchDate() {
		return latestIncludedMatchDate;
	}

	public void setLatestIncludedMatchDate(Calendar latestIncludedMatchDate) {
		this.latestIncludedMatchDate = latestIncludedMatchDate;
	}

	public String getLatestIncludedMatchName() {
		return latestIncludedMatchName;
	}

	public void setLatestIncludedMatchName(String latestIncludedMatchName) {
		this.latestIncludedMatchName = latestIncludedMatchName;
	}
}
