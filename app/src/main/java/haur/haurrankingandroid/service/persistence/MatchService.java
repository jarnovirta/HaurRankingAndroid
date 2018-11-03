package haur.haurrankingandroid.service.persistence;

import java.util.List;

import haur.haurrankingandroid.activity.fragment.MatchesTabFragment;
import haur.haurrankingandroid.service.task.DeleteMatchesTask;

/**
 * Created by Jarno on 24.10.2018.
 */

public class MatchService {
	public static void deleteAll(List<Long> matchIds) {
		new DeleteMatchesTask(matchIds).execute();
	}


}
