package haur.haurrankingandroid.activity.fragment;

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
import haur.haurrankingandroid.event.AppEvent;
import haur.haurrankingandroid.event.AppEventListener;
import haur.haurrankingandroid.event.AppEventService;
import haur.haurrankingandroid.event.DatabaseUpdatedEvent;
import haur.haurrankingandroid.service.persistence.MatchService;
import haur.haurrankingandroid.service.task.LoadMatchListTask;
import haur.haurrankingandroid.domain.MatchListItem;

/**
 * Created by Jarno on 20.10.2018.
 */

public class MatchesTabFragment extends ListFragment
		implements ActionMode.Callback, AppEventListener {

	private static List<MatchListItem> matchList = new ArrayList<>();
	private static boolean matchListSet = false;
	private MatchListAdapter adapter;
	private boolean actionMode = false;

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
		adapter.setItemsSelectable(actionMode);
		setListAdapter(adapter);
		if (!matchListSet) {
			new LoadMatchListTask(this).execute();
			matchListSet = true;
		}
		AppEventService.addListener(this);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("TEST", "LONG CLICK");
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

	public void process(AppEvent event) {
		if (event instanceof DatabaseUpdatedEvent) {
			new LoadMatchListTask(this).execute();
		}
	}
}
