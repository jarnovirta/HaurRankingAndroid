package haur.haurrankingandroid.haur.haurranking.domain;

/**
 * Created by Jarno on 11.10.2018.
 */

public enum PowerFactor {
	MAJOR, MINOR, SUBMINOR;

	@Override
	public String toString() {
		return super.toString().substring(0, 1).toUpperCase()
				+ super.toString().substring(1).toLowerCase();
	}
}
