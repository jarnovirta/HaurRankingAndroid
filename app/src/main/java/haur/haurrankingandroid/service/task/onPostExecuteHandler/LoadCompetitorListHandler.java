package haur.haurrankingandroid.service.task.onPostExecuteHandler;

import java.util.List;

import haur.haurrankingandroid.domain.CompetitorListItem;

/**
 * Created by Jarno on 28.10.2018.
 */

public interface LoadCompetitorListHandler {

	void process(List<CompetitorListItem> competitorListItems);
}
