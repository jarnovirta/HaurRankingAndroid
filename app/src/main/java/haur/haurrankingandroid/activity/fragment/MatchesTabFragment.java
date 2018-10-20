package haur.haurrankingandroid.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.activity.adapter.MatchListAdapter;
import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.MatchListItem;

/**
 * Created by Jarno on 20.10.2018.
 */

public class MatchesTabFragment extends ListFragment {

	private static List<MatchListItem> matchList = new ArrayList<>();
	private static boolean matchListSet = false;
	private MatchListAdapter adapter;

	public void setMatchList(List<MatchListItem> items) {
		matchList.clear();
		for (MatchListItem item : items) matchList.add(item);
		adapter.notifyDataSetChanged();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.db_matches_tab_fragment, container, false);
		adapter = new MatchListAdapter(this.getActivity(),
				matchList);
		setListAdapter(adapter);
		if (!matchListSet) {
			new LoadMatchListTask(this).execute();
			matchListSet = true;
		}
		return view;
	}


}
