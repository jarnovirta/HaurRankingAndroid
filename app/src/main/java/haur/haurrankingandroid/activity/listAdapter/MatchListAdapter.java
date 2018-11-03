package haur.haurrankingandroid.activity.listAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.MatchListItem;
import haur.haurrankingandroid.util.DataFormatUtils;

/**
 * Created by Jarno on 20.10.2018.
 */

public class MatchListAdapter extends ArrayAdapter<MatchListItem>  {
	private final Context context;
	private final List<MatchListItem> matchList;

	private boolean itemsSelectable = false;

	public MatchListAdapter(Context context, List<MatchListItem> matchList) {
		super(context, -1, matchList);
		this.context = context;
		this.matchList = matchList;
	}

	private static class ViewHolder {
		private TextView name;
		private TextView date;
		private TextView classifiers;
		private CheckBox selectMatchCheckBox;

	}
	@NonNull
	@Override
	public View getView(final int position, View recycledView, @NonNull ViewGroup parent) {
		ViewHolder viewHolder;

		if (recycledView == null || recycledView.getTag() == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			viewHolder = new ViewHolder();

			recycledView = inflater.inflate(R.layout.match_list_item, parent, false);
			viewHolder.name = (TextView) recycledView.findViewById(R.id.match_name);
			viewHolder.date = (TextView) recycledView.findViewById(R.id.match_date);
			viewHolder.classifiers = (TextView) recycledView.findViewById(R.id.classifiers_list_view);
			viewHolder.selectMatchCheckBox = (CheckBox) recycledView.findViewById(R.id.select_match_checkbox);
			recycledView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) recycledView.getTag();
		}

		final MatchListItem item = matchList.get(position);
		viewHolder.name.setText(item.getMatch().getName());
		viewHolder.date.setText(DataFormatUtils.dateToString(item.getMatch().getDate()));
		String classifiersList = "";

		for (Classifier classifier : item.getClassifiers()) {
			if (item.getClassifiers().indexOf(classifier) > 0) classifiersList += ", ";
			classifiersList += classifier.toString();
		}
		viewHolder.classifiers.setText(classifiersList);

		if (itemsSelectable) {
			viewHolder.selectMatchCheckBox.setVisibility(View.VISIBLE);
			viewHolder.selectMatchCheckBox.setChecked(item.isSelected());
			viewHolder.selectMatchCheckBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					item.setSelected(((CheckBox) v).isChecked());
				}
			});
		}
		else viewHolder.selectMatchCheckBox.setVisibility(View.GONE);

		return recycledView;
	}

	public void setItemsSelectable(boolean selectable) {
		this.itemsSelectable = selectable;
		notifyDataSetChanged();
	}
}
