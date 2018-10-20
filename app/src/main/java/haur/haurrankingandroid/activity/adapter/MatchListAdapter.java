package haur.haurrankingandroid.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.domain.MatchListItem;
import haur.haurrankingandroid.util.DataFormatUtils;

/**
 * Created by Jarno on 20.10.2018.
 */

public class MatchListAdapter extends ArrayAdapter<MatchListItem>  {
	private final Context context;
	private final List<MatchListItem> matchList;

	public MatchListAdapter(Context context, List<MatchListItem> matchList) {
		super(context, -1, matchList);
		this.context = context;
		this.matchList = matchList;
	}

	private static class ViewHolder {
		private TextView name;
		private TextView date;
		private TextView classifiers;

	}
	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			viewHolder = new ViewHolder();

			convertView = inflater.inflate(R.layout.match_list_item, parent, false);
			viewHolder.name = (TextView) convertView.findViewById(R.id.match_name);
			viewHolder.date = (TextView) convertView.findViewById(R.id.match_date);
			viewHolder.classifiers = (TextView) convertView.findViewById(R.id.classifiers_list_view);

		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		MatchListItem item = matchList.get(position);
		viewHolder.name.setText(item.getMatch().getName());
		viewHolder.date.setText(DataFormatUtils.dateToString(item.getMatch().getDate()));

		String classifiersList = "";

		for (String classifier : item.getClassifiers()) {
			if (item.getClassifiers().indexOf(classifier) > 0) classifiersList += ", ";
			classifiersList += classifier.toString();
		}

		viewHolder.classifiers.setText(classifiersList);

		return convertView;
	}

}
