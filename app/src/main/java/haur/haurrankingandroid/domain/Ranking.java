package haur.haurrankingandroid.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import haur.haurrankingandroid.data.dao.TypeConverters.DateConverter;

/**
 * Created by Jarno on 14.10.2018.
 */

@Entity
public class Ranking {

	@PrimaryKey(autoGenerate = true)
	private Long id;

	@Ignore
	private List<DivisionRanking> divisionRankings = new ArrayList<DivisionRanking>();

	@TypeConverters(DateConverter.class)
	private Date date;

	@Ignore
	private int totalResultsCount;

	@Ignore
	private int competitorsWithRank;

	@Ignore
	private int validClassifiersCount;

	public Ranking() {
		date = new Date();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<DivisionRanking> getDivisionRankings() {
		return divisionRankings;
	}


	public void setDivisionRankings(List<DivisionRanking> divisionRankings) {
		this.divisionRankings = divisionRankings;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

}
