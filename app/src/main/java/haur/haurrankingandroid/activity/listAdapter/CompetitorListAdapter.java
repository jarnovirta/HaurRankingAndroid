package haur.haurrankingandroid.activity.listAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.CompetitorListItem;

/**
 * Created by Jarno on 20.10.2018.
 */

public class CompetitorListAdapter extends ArrayAdapter<CompetitorListItem> {
	private final Context context;
	private final List<CompetitorListItem> competitorList;
	private boolean itemsSelectable = false;

	public CompetitorListAdapter(Context context, List<CompetitorListItem> competitorList) {
		super(context, -1, competitorList);
		this.context = context;
		this.competitorList = competitorList;
	}

	private static class ViewHolder {
		private TextView competitorName;
		private CheckBox selectCompetitorCheckbox;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View recycledView, @NonNull ViewGroup parent) {
		ViewHolder viewHolder;
		if (recycledView == null || recycledView.getTag() == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			recycledView = inflater.inflate(R.layout.competitor_list_item, parent, false);
			viewHolder.competitorName = (TextView) recycledView.findViewById(R.id.competitor_name);
			viewHolder.selectCompetitorCheckbox = (CheckBox) recycledView.findViewById(R.id.select_competitor_checkbox);
			recycledView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) recycledView.getTag();

		}
		Competitor comp = competitorList.get(position).getCompetitor();
		String name = comp.getLastName() + ", " + comp.getFirstName();
		viewHolder.competitorName.setText(name);

		if (itemsSelectable) {
			viewHolder.selectCompetitorCheckbox.setVisibility(View.VISIBLE);
			viewHolder.selectCompetitorCheckbox.setChecked(competitorList.get(position).isSelected());
		}
		else viewHolder.selectCompetitorCheckbox.setVisibility(View.GONE);

		return recycledView;
	}
	public void setItemsSelectable(boolean selectable) {
		this.itemsSelectable = selectable;
		notifyDataSetChanged();
	}
}