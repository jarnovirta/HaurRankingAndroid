package haur.haurrankingandroid.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jarno on 14.10.2018.
 */

public class Ranking {
	private Division division;


	private List<RankingRow> rankingRows = new ArrayList<RankingRow>();

	private Set<Classifier> validClassifiers = new HashSet<Classifier>();

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public List<RankingRow> getRankingRows() {
		return rankingRows;
	}

	public void setRankingRows(List<RankingRow> rankingRows) {
		this.rankingRows = rankingRows;
	}

	public Set<Classifier> getValidClassifiers() {
		return validClassifiers;
	}

	public void setValidClassifiers(Set<Classifier> validClassifiers) {
		this.validClassifiers = validClassifiers;
	}
}
