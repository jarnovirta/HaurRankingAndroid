package haur.haurrankingandroid.domain;

import java.util.List;

/**
 * Created by Jarno on 20.10.2018.
 */

public class MatchListItem {
	private Match match;
	private List<Classifier> classifiers;
	boolean selected = false;

	public MatchListItem(Match match, List<Classifier> classifiers) {
		this.match = match;
		this.classifiers = classifiers;
	}
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public List<Classifier> getClassifiers() {
		return classifiers;
	}

	public void setClassifiers(List<Classifier> classifiers) {
		this.classifiers = classifiers;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
