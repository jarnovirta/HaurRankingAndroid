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

}
