package haur.haurrankingandroid.domain;

import android.support.annotation.NonNull;


/**
 * Created by Jarno on 25.10.2018.
 */

public class CompetitorListItem implements Comparable<CompetitorListItem> {
	private Competitor competitor;
	private boolean selected = false;

	public CompetitorListItem(Competitor competitor) {
		this.competitor = competitor;
	}

	@Override
	public int compareTo(@NonNull CompetitorListItem other) {
		int result = this.competitor.getLastName().compareTo(other.getCompetitor().getLastName());
		if (result != 0) return result;
		return this.competitor.getFirstName().compareTo(other.getCompetitor().getFirstName());
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
