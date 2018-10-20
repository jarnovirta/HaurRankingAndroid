package haur.haurrankingandroid.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.activity.adapter.RankingListAdapter;
import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.DivisionRankingRow;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.service.ranking.GenerateRankingResponseHandler;
import haur.haurrankingandroid.service.ranking.GenerateRankingTask;

/**
 * Created by Jarno on 13.10.2018.
 */

public class RankingFragment extends ListFragment {

	private static DivisionRanking currentDivisionRanking;
	private static List<DivisionRankingRow> currentRows = new ArrayList<>();
	private static boolean ranking = false;
	private RankingListAdapter adapter;

	public void setRanking(Ranking ranking) {
		if (ranking.getDivisionRankings() != null && ranking.getDivisionRankings().size() > 0) {
			currentDivisionRanking = ranking.getDivisionRankings().get(0);
			if (currentDivisionRanking.getRows() != null) {
				currentRows.clear();
				for (DivisionRankingRow row : currentDivisionRanking.getRows()) {
					if (row.isRankedCompetitor()) currentRows.add(row);
				}
			}
		}
		adapter.notifyDataSetChanged();
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
	                         ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ranking, container, false);
		adapter = new RankingListAdapter(this.getActivity(),
				currentRows);
		setListAdapter(adapter);

		if (!ranking) {
			new GenerateRankingTask(new RankingGeneratedHandler()).execute();
			ranking = true;
		}

		return view;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private class RankingGeneratedHandler implements GenerateRankingResponseHandler {
		@Override
		public void process(Ranking ranking) {
			setRanking(ranking);

		}
	}

}
