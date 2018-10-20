package haur.haurrankingandroid.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.data.AppDatabase;

/**
 * Created by Jarno on 13.10.2018.
 */

public class BrowseDatabaseFragment extends Fragment {

	private FragmentTabHost tabHost;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		tabHost = new FragmentTabHost(getActivity());
		tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_browse_db);

		tabHost.addTab(tabHost.newTabSpec("db_competitions_tab").setIndicator("Luokitteluammunnat"),
				MatchesTabFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("db_competitors_tab").setIndicator("Kilpailijat"),
				CompetitorsTabFragment.class, null);

		new LoadStatisticsTask(this).execute();

		return tabHost;
	}

	public void updateTabTitles(int competitionsCount, int competitorsCount) {

		View tabView = tabHost.getTabWidget().getChildAt(0);
		String competitionsTabTitle = "Luokitteluammunnat (" + competitionsCount + ")";
		((TextView) tabView.findViewById(android.R.id.title)).setText(competitionsTabTitle);
		String competitorsTabTitle = "Kilpailijat (" + competitorsCount + ")";
		tabView = tabHost.getTabWidget().getChildAt(1);
		((TextView) tabView.findViewById(android.R.id.title)).setText(competitorsTabTitle);

	}
	@Override
	public void onPause() {
		super.onPause();
	}


}
