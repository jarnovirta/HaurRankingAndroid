package haur.haurrankingandroid.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.activity.listAdapter.RankingListAdapter;
import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.DivisionRankingRow;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.service.ranking.RankingService;


/**
 * Created by Jarno on 13.10.2018
 */

public class RankingFragment extends ListFragment {

	private static Ranking ranking;
	private static DivisionRanking currentDivisionRanking;
	private static List<DivisionRankingRow> currentRows = new ArrayList<>();
	private RankingListAdapter adapter;
	private static List<String> divisionSpinnerItems = new ArrayList<>();

	private Spinner divisionSpinner;

	@Override
	public View onCreateView(LayoutInflater inflater,
	                         ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ranking, container, false);
		setSpinner(view);
		adapter = new RankingListAdapter(this.getActivity(),
				currentRows);
		setListAdapter(adapter);
		RankingService.getRanking().observe(this, newRanking -> updateView(newRanking));

		return view;
	}

	private void updateView(Ranking newRanking) {
		setRanking(newRanking);
		if (ranking != null) populateDivisionSpinner();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private void setSpinner(View view) {
		divisionSpinner = view.findViewById(R.id.division_spinner);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
				android.R.layout.simple_spinner_item, divisionSpinnerItems);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		divisionSpinner.setAdapter(dataAdapter);
		divisionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				setCurrentDivision(position);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}
	public void setRanking(Ranking newRanking) {
		ranking = newRanking;
		setCurrentDivision(0);

	}

	private void setCurrentDivision(int divisionIndex) {
		currentRows.clear();
		if (ranking.getDivisionRankings() != null && ranking.getDivisionRankings().size() > 0) {
			currentDivisionRanking = ranking.getDivisionRankings().get(divisionIndex);
			if (currentDivisionRanking.getRows() != null) {
				for (DivisionRankingRow row : currentDivisionRanking.getRows()) {
					if (row.isRankedCompetitor()) currentRows.add(row);
				}
			}
		}
	}

	public void populateDivisionSpinner() {
		boolean spinnerVisible = false;
		if (ranking.getDivisionRankings() != null) {
			List<String> divisions = new ArrayList<>();
			for (DivisionRanking divRank : ranking.getDivisionRankings()) {
				divisions.add(divRank.getDivision().toString());
			}
			if (divisions.size() > 0) {
				spinnerVisible = true;
				divisionSpinner.setVisibility(View.VISIBLE);
				divisionSpinnerItems.clear();
				for (String div : divisions) {
					divisionSpinnerItems.add(div);
				}
				((ArrayAdapter<String>) divisionSpinner.getAdapter()).notifyDataSetChanged();

			}
		}
		if (spinnerVisible) divisionSpinner.setVisibility(View.VISIBLE);
		else divisionSpinner.setVisibility(View.GONE);
	}
}
