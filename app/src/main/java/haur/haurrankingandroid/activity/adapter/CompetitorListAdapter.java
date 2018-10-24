package haur.haurrankingandroid.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.domain.Competitor;

/**
 * Created by Jarno on 20.10.2018.
 */

public class CompetitorListAdapter extends ArrayAdapter<Competitor> {


	private final Context context;
	private final List<Competitor> competitorList;

	public CompetitorListAdapter(Context context, List<Competitor> competitorList) {
		super(context, -1, competitorList);
		this.context = context;
		this.competitorList = competitorList;
	}

	private static class ViewHolder {
		private TextView competitorName;
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

			recycledView.setTag(viewHolder);

		}
		else {
			viewHolder = (ViewHolder) recycledView.getTag();

		}
		Competitor comp = competitorList.get(position);
		String name = comp.getLastName() + ", " + comp.getFirstName();
		viewHolder.competitorName.setText(name);

		return recycledView;
	}

}