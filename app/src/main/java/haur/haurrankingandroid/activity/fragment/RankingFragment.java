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
import haur.haurrankingandroid.event.AppEvent;
import haur.haurrankingandroid.event.AppEventListener;
import haur.haurrankingandroid.event.AppEventService;
import haur.haurrankingandroid.event.DatabaseUpdatedEvent;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.GenerateRankingPostExecuteHandler;
import haur.haurrankingandroid.service.task.GenerateRankingTask;

/**
 * Created by Jarno on 13.10.2018.
 */

public class RankingFragment extends ListFragment
		implements AppEventListener, GenerateRankingPostExecuteHandler {

	private static Ranking ranking;
	private static DivisionRanking currentDivisionRanking;
	private static List<DivisionRankingRow> currentRows = new ArrayList<>();
	private static boolean rankingSet = false;
	private RankingListAdapter adapter;
	private static List<String> divisionSpinnerItems = new ArrayList<>();

	private Spinner divisionSpinner;

	@Override
	public View onCreateView(LayoutInflater inflater,
	                         ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ranking, container, false);

		adapter = new RankingListAdapter(this.getActivity(),
				currentRows);
		setListAdapter(adapter);

		if (!rankingSet) {
			new GenerateRankingTask(this).execute();
			rankingSet = true;
		}
		setSpinner(view);
		if (currentDivisionRanking != null) populateDivisionSpinner();

		AppEventService.addListener(this);
		return view;
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

	@Override
	public void process(Ranking ranking) {
			setRanking(ranking);
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
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}
	public void setRanking(Ranking newRanking) {
		Log.d("TEST", "**** SETTING RANKING");
		ranking = newRanking;
		if (ranking.getDivisionRankings() != null && ranking.getDivisionRankings().size() > 0) {
			setCurrentDivision(0);
		}
		populateDivisionSpinner();
		((ArrayAdapter) divisionSpinner.getAdapter()).notifyDataSetChanged();
	}

	private void setCurrentDivision(int divisionIndex) {
		currentDivisionRanking = ranking.getDivisionRankings().get(divisionIndex);
		if (currentDivisionRanking.getRows() != null) {
			currentRows.clear();
			for (DivisionRankingRow row : currentDivisionRanking.getRows()) {
				if (row.isRankedCompetitor()) currentRows.add(row);
			}
		}
		adapter.notifyDataSetChanged();

	}

	public void populateDivisionSpinner() {
		List<String> divisions = new ArrayList<>();
		for (DivisionRanking divRank : ranking.getDivisionRankings()) {
			divisions.add(divRank.getDivision().toString());
		}
		divisionSpinnerItems.clear();
		for (String div : divisions) {
			divisionSpinnerItems.add(div.toString());
		}

	}

	@Override
	public void process(AppEvent event) {
		if (event instanceof DatabaseUpdatedEvent) {
			Log.d("TEST", "***** GENERATING NEW RANKING ***** ");
			new GenerateRankingTask(this).execute();
		}
	}
}
