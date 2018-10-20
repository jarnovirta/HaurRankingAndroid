package haur.haurrankingandroid.domain;

import java.util.List;

/**
 * Created by Jarno on 20.10.2018.
 */

public class MatchListItem {
	Match match;
	List<String> classifiers;

	public MatchListItem(Match match, List<String> classifiers) {
		this.match = match;
		this.classifiers = classifiers;
	}
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public List<String> getClassifiers() {
		return classifiers;
	}

	public void setClassifiers(List<String> classifiers) {
		this.classifiers = classifiers;
	}
}
