package haur.haurrankingandroid.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Jarno on 3.11.2018.
 */

@Entity
public class RankingDataChangedEntity {
	@PrimaryKey(autoGenerate = true)
	private Long id;

	boolean dataChanged;

	public RankingDataChangedEntity(boolean dataChanged) {
		this.dataChanged = dataChanged;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDataChanged() {
		return dataChanged;
	}

	public void setDataChanged(boolean dataChanged) {
		this.dataChanged = dataChanged;
	}
}
