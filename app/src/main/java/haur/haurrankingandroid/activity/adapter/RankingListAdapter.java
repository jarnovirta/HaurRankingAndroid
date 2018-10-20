package haur.haurrankingandroid.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.domain.DivisionRankingRow;
import haur.haurrankingandroid.util.DataFormatUtils;

/**
 * Created by Jarno on 15.10.2018.
 */

public class RankingListAdapter extends ArrayAdapter<DivisionRankingRow>  {
	private final Context context;
	private final List<DivisionRankingRow> rankingRows;

	public RankingListAdapter(Context context, List<DivisionRankingRow> rankingRows) {
		super(context, -1, rankingRows);
		this.context = context;
		this.rankingRows = rankingRows;
	}

	private static class ViewHolder {
		private TextView rankAndNameView;
		private TextView resultCountView;
		private TextView percentView;
		private TextView hfAvgView;

	}

	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.ranking_list_item, parent, false);

			viewHolder.rankAndNameView = (TextView) convertView.findViewById(R.id.rankAndNameView);
			viewHolder.resultCountView = (TextView) convertView.findViewById(R.id.resultCountView);
			viewHolder.hfAvgView = (TextView) convertView.findViewById(R.id.hitfactorAvgView);
			viewHolder.percentView = (TextView) convertView.findViewById(R.id.percentView);;

		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		DivisionRankingRow row = rankingRows.get(position);
		String name = row.getCompetitor().getFirstName() + " " + row.getCompetitor().getLastName();

		viewHolder.rankAndNameView.setText((position + 1) + ". " + name);
		viewHolder.resultCountView.setText(String.valueOf(row.getResultsCount()) + " tulosta");

		Double percentage = DataFormatUtils.round(row.getResultPercentage(), 2);
		String percentageString = doubleToString(percentage) + " %";
		viewHolder.percentView.setText(percentageString);

		Double hfAverage = DataFormatUtils.round(row.getHitFactorAverage(), 2);
		String hfString = "HF-keskiarvo: " + doubleToString(hfAverage);
		viewHolder.hfAvgView.setText(hfString);

		return convertView;
	}

	private String doubleToString(double number) {
		String result = String.valueOf(number);
		if (result.indexOf(".") == result.length() - 2) result += "0";
		return result;
	}
}
