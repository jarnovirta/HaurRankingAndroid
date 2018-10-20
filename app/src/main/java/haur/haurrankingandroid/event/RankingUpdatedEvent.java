package haur.haurrankingandroid.event;

import haur.haurrankingandroid.domain.Ranking;

/**
 * Created by Jarno on 20.10.2018.
 */

public class RankingUpdatedEvent extends AppEvent {
	Ranking ranking;

	public RankingUpdatedEvent(Ranking ranking) {
		this.ranking = ranking;
	}
	public Ranking getRanking() {
		return ranking;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}
}
