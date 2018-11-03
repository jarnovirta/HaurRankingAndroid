package haur.haurrankingandroid.service.task.onPostExecuteHandler;

import java.util.List;

import haur.haurrankingandroid.domain.MatchListItem;

/**
 * Created by Jarno on 28.10.2018.
 */

public interface LoadMatchListHandler {
	void process(List<MatchListItem> matchListItems);
}
