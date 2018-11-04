package haur.haurrankingandroid.activity.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import haur.haurrankingandroid.R;
import haur.haurrankingandroid.activity.listAdapter.MatchListAdapter;
import haur.haurrankingandroid.service.persistence.MatchService;
import haur.haurrankingandroid.service.ranking.RankingService;
import haur.haurrankingandroid.service.task.LoadMatchListTask;
import haur.haurrankingandroid.domain.MatchListItem;

/**
 * Created by Jarno on 20.10.2018.
 */

public class MatchesTabFragment extends ListFragment
		implements ActionMode.Callback {

	private List<MatchListItem> matchList = new ArrayList<>();
	private MatchListAdapter adapter;
	private boolean actionMode = false;
	private BrowseDatabaseViewModel viewModel;

	public void setMatchList(List<MatchListItem> items) {
		matchList.clear();
		matchList.addAll(items);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewModel = ViewModelProviders.of(getActivity()).get(BrowseDatabaseViewModel.class);;
		viewModel.getMatchListItems().observe(this, newMatchList -> setMatchList(newMatchList));
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.db_matches_tab_fragment, container, false);
		adapter = new MatchListAdapter(this.getActivity(),
				matchList);
		adapter.setItemsSelectable(actionMode);
		setListAdapter(adapter);

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (!actionMode) {
					MatchListItem item = matchList.get(position);
					item.setSelected(true);
					goToActionMode(item);
				}
				else onListItemClick(getListView(), view, position, id);
				return true;
			}
		});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (actionMode == true) {
			super.onListItemClick(l, v, position, id);
			matchList.get(position).setSelected(!matchList.get(position).isSelected());
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.delete_items_action_bar, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}


	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_delete:
				List<Long> ids = new ArrayList<>();
				for (MatchListItem matchListItem : matchList) {
					if (matchListItem.isSelected()) ids.add(matchListItem.getMatch().getId());
				}
				MatchService.deleteAll(ids);
				viewModel.update();
				break;
		}
		return true;
	}

	private void goToActionMode(MatchListItem item) {
		getListView().startActionMode(this);
		actionMode = true;
		adapter.setItemsSelectable(true);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		actionMode = false;
		adapter.setItemsSelectable(false);
		adapter.notifyDataSetChanged();
	}

}
