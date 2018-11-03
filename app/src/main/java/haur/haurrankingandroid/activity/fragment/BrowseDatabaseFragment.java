package haur.haurrankingandroid.activity.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.domain.MatchListItem;
import haur.haurrankingandroid.service.task.LoadStatisticsTask;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.LoadStatisticsPostExecuteHandler;

/**
 * Created by Jarno on 13.10.2018.
 */

public class BrowseDatabaseFragment extends Fragment {

	private FragmentTabHost tabHost;
	private BrowseDatabaseViewModel viewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewModel = ViewModelProviders.of(getActivity()).get(BrowseDatabaseViewModel.class);
		viewModel.getMatchListItems().observe(this, newList -> {
			updateTabTitles();
		});
		viewModel.getCompetitorListItems().observe(this, newList -> {
			updateTabTitles();
		});

	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		tabHost = new FragmentTabHost(getActivity());
		tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_browse_db);
		tabHost.addTab(tabHost.newTabSpec("db_competitions_tab").setIndicator("Kilpailut"),
				MatchesTabFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("db_competitors_tab").setIndicator("Kilpailijat"),
				CompetitorsTabFragment.class, null);

		return tabHost;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public void updateTabTitles() {

		Integer competitionsCount = null;
		Integer competitorsCount = null;

		if (viewModel.getMatchListItems() != null
				&& viewModel.getMatchListItems().getValue() != null) {
			competitionsCount = viewModel.getMatchListItems().getValue().size();
		}
		if (viewModel.getCompetitorListItems() != null
				&& viewModel.getCompetitorListItems().getValue() != null) {
			competitorsCount = viewModel.getCompetitorListItems().getValue().size();
		}

		View tabView = tabHost.getTabWidget().getChildAt(0);
		String competitionsTabTitle = "Kilpailut";
		if (competitionsCount != null) competitionsTabTitle += " (" + competitionsCount + ")";
		((TextView) tabView.findViewById(android.R.id.title)).setText(competitionsTabTitle);

		String competitorsTabTitle = "Kilpailijat";
		if (competitorsCount != null) competitorsTabTitle += " (" + competitorsCount + ")";
		tabView = tabHost.getTabWidget().getChildAt(1);
		((TextView) tabView.findViewById(android.R.id.title)).setText(competitorsTabTitle);
	}
}
