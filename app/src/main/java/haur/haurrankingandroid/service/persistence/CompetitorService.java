package haur.haurrankingandroid.service.persistence;

import java.util.List;

import haur.haurrankingandroid.service.task.DeleteCompetitorsTask;

/**
 * Created by Jarno on 25.10.2018.
 */

public class CompetitorService {
	public static void deleteAll(List<Long> ids) {
		new DeleteCompetitorsTask(ids).execute();
	}
}



