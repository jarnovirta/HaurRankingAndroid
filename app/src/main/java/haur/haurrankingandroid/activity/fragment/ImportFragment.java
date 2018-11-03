package haur.haurrankingandroid.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.activity.listAdapter.StageListAdapter;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.MatchScore;
import haur.haurrankingandroid.domain.ScoreCard;
import haur.haurrankingandroid.domain.Stage;
import haur.haurrankingandroid.domain.StageListItem;
import haur.haurrankingandroid.domain.StageScore;
import haur.haurrankingandroid.service.practiScore.PractiScoreDataService;
import haur.haurrankingandroid.service.ranking.RankingService;
import haur.haurrankingandroid.service.task.SaveMatchTask;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.SaveMatchTaskResponseHandler;
import haur.haurrankingandroid.util.DataFormatUtils;

/**
 * Created by Jarno on 13.10.2018.
 */

public class ImportFragment extends ListFragment {

	private Match match;

	private List<StageListItem> stageListItems = new ArrayList<>();
	private StageListAdapter adapter;
	private final int CHOOSE_FILE_REQUEST_CODE = 1;
	private TextView matchNameTextView;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_import, container, false);
		matchNameTextView = view.findViewById(R.id.import_match_name);
		Button importButton = view.findViewById(R.id.import_button);
		importButton.setOnClickListener(v -> {
			importResults();
		});

		if (match != null) matchNameTextView.setText(match.getName() + " (" + DataFormatUtils.dateToString(match.getDate()) + ")");
		adapter = new StageListAdapter(this.getActivity(), stageListItems);
		setListAdapter(adapter);

		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.setType("*/*");
		startActivityForResult(intent, CHOOSE_FILE_REQUEST_CODE);
		return view;
	}

	private void importResults() {
		List<ScoreCard> saveCards = new ArrayList<>();
		for (StageListItem item : stageListItems) {
			if (item.isSelected()) {
				for (ScoreCard card : match.getScoreCards()) {
					if (card.getStagePractiScoreId().equals(item.getStage().getPractiScoreId())) {
						card.setClassifier(item.getClassifier());
						saveCards.add(card);
					}
				}
			}
		}
		match.setScoreCards(saveCards);
		List<Competitor> competitors = new ArrayList<>();
		for (ScoreCard card : match.getScoreCards()) {
			if (!competitors.contains(card.getCompetitor())) competitors.add(card.getCompetitor());

		}
		match.setCompetitors(competitors);
		new SaveMatchTask(() -> {
			RankingService.generateRanking();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
					new RankingFragment()).commit();
		}, getActivity()).execute(match);
	}
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHOOSE_FILE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
			if (data.getData() != null) {
				match = PractiScoreDataService.readMatchFromFile(data.getData());
				matchNameTextView.setText(match.getName() + " (" + DataFormatUtils.dateToString(match.getDate()) + ")");

				stageListItems.clear();
				for (Stage stage : match.getStages()) {
					stageListItems.add(new StageListItem(stage));
				}
				adapter.notifyDataSetChanged();
			}
		}
	}

}
