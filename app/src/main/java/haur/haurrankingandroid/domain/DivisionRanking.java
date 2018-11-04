package haur.haurrankingandroid.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Jarno on 14.10.2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Ranking.class,
		parentColumns = "id",
		childColumns = "rankingId",
		onDelete = CASCADE))
public class DivisionRanking {
	@PrimaryKey(autoGenerate = true)
	private Long id;

	private Long rankingId;

	private Division division;

	@Ignore
	private List<DivisionRankingRow> rows = new ArrayList<DivisionRankingRow>();

	@Ignore
	List<Classifier> validClassifiers;

	public DivisionRanking() {}
	public DivisionRanking(Division division, List<DivisionRankingRow> rows,
	                       List<Classifier> validClassifiers) {
		this.division = division;
		this.rows = rows;
		this.validClassifiers = validClassifiers;
	}

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

	public List<Classifier> getValidClassifiers() {
		return validClassifiers;
	}

	public void setValidClassifiers(List<Classifier> validClassifiers) {
		this.validClassifiers = validClassifiers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRankingId() {
		return rankingId;
	}

	public void setRankingId(Long rankingId) {
		this.rankingId = rankingId;
	}
}
