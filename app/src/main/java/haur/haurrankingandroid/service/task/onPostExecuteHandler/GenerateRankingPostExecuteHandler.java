package haur.haurrankingandroid.service.task.onPostExecuteHandler;

import haur.haurrankingandroid.domain.Ranking;

/**
 * Created by Jarno on 14.10.2018.
 */

public interface GenerateRankingPostExecuteHandler {
	void process(Ranking ranking);
}
