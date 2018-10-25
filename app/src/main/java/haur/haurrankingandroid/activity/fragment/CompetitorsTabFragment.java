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
import java.util.Collections;
import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.activity.listAdapter.CompetitorListAdapter;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.CompetitorListItem;
import haur.haurrankingandroid.domain.MatchListItem;
import haur.haurrankingandroid.event.AppEvent;
import haur.haurrankingandroid.event.AppEventListener;
import haur.haurrankingandroid.event.AppEventService;
import haur.haurrankingandroid.event.DatabaseUpdatedEvent;
import haur.haurrankingandroid.service.persistence.CompetitorService;
import haur.haurrankingandroid.service.persistence.MatchService;
import haur.haurrankingandroid.service.task.LoadCompetitorListTask;
import haur.haurrankingandroid.service.task.LoadMatchListTask;

/**
 * Created by Jarno on 20.10.2018.
 */

public class CompetitorsTabFragment extends ListFragment
		implements ActionMode.Callback, AppEventListener {
	private static List<CompetitorListItem> competitorItems = new ArrayList<>();
	private static boolean competitorsSet = false;
	private CompetitorListAdapter adapter;
	private boolean actionMode = false;

	public void setCompetitors(List<CompetitorListItem> newCompetitors) {
		competitorItems.clear();
		Collections.sort(newCompetitors);
		for (CompetitorListItem comp : newCompetitors) competitorItems.add(comp);
		adapter.notifyDataSetChanged();
	}
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.db_competitors_tab_fragment, container, false);
		adapter = new CompetitorListAdapter(this.getActivity(),
				competitorItems);
		setListAdapter(adapter);
		if (!competitorsSet) {
			new LoadCompetitorListTask(this).execute();
			competitorsSet = true;
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
					CompetitorListItem item = competitorItems.get(position);
					item.setSelected(true);
					goToActionMode(item);
				}
				else onListItemClick(getListView(), view, position, id);
				return true;
			}
		});
	}
	private void goToActionMode(CompetitorListItem item) {
		getListView().startActionMode(this);
		actionMode = true;
		adapter.setItemsSelectable(true);
		adapter.notifyDataSetChanged();
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
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (actionMode == true) {
			super.onListItemClick(l, v, position, id);
			competitorItems.get(position).setSelected(!competitorItems.get(position).isSelected());
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_delete:
				List<Long> ids = new ArrayList<>();
				for (CompetitorListItem compListItem : competitorItems) {
					if (compListItem.isSelected()) ids.add(compListItem.getCompetitor().getId());
				}
				CompetitorService.deleteAll(ids);
				new LoadCompetitorListTask(this).execute();
				break;
		}
		return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		actionMode = false;
		adapter.setItemsSelectable(false);
		adapter.notifyDataSetChanged();
	}
	public void process(AppEvent event) {
		if (event instanceof DatabaseUpdatedEvent) {
			new LoadCompetitorListTask(this).execute();
		}
	}
}

