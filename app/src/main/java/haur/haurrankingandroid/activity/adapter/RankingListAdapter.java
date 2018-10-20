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
	private ViewHolder viewHolder;
	private LayoutInflater inflater;

	public RankingListAdapter(Context context, List<DivisionRankingRow> rankingRows) {
		super(context, R.layout.fragment_ranking, rankingRows);
		this.context = context;
		this.rankingRows = rankingRows;
		inflater = LayoutInflater.from(context);
	}

	private static class ViewHolder {
		private TextView rankAndNameView;
		private TextView resultCountView;
		private TextView percentView;
		private TextView hfAvgView;

	}

	@NonNull
	@Override
	public View getView(int position, View recycledView, @NonNull ViewGroup parent) {
		if (recycledView == null || recycledView.getTag() == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();

			recycledView = inflater.inflate(R.layout.ranking_list_item, parent, false);

			viewHolder.rankAndNameView = (TextView) recycledView.findViewById(R.id.rankAndNameView);
			viewHolder.resultCountView = (TextView) recycledView.findViewById(R.id.resultCountView);
			viewHolder.hfAvgView = (TextView) recycledView.findViewById(R.id.hitfactorAvgView);
			viewHolder.percentView = (TextView) recycledView.findViewById(R.id.percentView);;

			recycledView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) recycledView.getTag();
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

		return recycledView;
	}

	private String doubleToString(double number) {
		String result = String.valueOf(number);
		if (result.indexOf(".") == result.length() - 2) result += "0";
		return result;
	}
}
