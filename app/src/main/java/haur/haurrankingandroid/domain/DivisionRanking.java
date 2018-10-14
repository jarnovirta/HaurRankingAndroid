package haur.haurrankingandroid.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jarno on 14.10.2018.
 */

public class DivisionRanking {
	private Division division;

	private List<DivisionRankingRow> rows = new ArrayList<DivisionRankingRow>();

	private Set<Classifier> validClassifiers = new HashSet<Classifier>();

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public List<DivisionRankingRow> getRows() {
		return rows;
	}

	public void setRows(List<DivisionRankingRow> rows) {
		this.rows = rows;
	}

	public Set<Classifier> getValidClassifiers() {
		return validClassifiers;
	}

	public void setValidClassifiers(Set<Classifier> validClassifiers) {
		this.validClassifiers = validClassifiers;
	}
}
