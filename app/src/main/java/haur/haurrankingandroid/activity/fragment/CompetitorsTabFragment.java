package haur.haurrankingandroid.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.activity.adapter.CompetitorListAdapter;
import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Competitor;

/**
 * Created by Jarno on 20.10.2018.
 */

public class CompetitorsTabFragment extends ListFragment {

	private static List<Competitor> competitors = new ArrayList<>();
	private static boolean competitorsSet = false;
	private CompetitorListAdapter adapter;

	public void setCompetitors(List<Competitor> newCompetitors) {
		competitors.clear();
		Collections.sort(newCompetitors);
		for (Competitor comp : newCompetitors) competitors.add(comp);
		adapter.notifyDataSetChanged();
	}
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.db_competitors_tab_fragment, container, false);
		adapter = new CompetitorListAdapter(this.getActivity(),
				competitors);
		setListAdapter(adapter);
		if (!competitorsSet) {
			new LoadCompetitorListTask(this).execute();
			competitorsSet = true;
		}
		return view;
	}
}

