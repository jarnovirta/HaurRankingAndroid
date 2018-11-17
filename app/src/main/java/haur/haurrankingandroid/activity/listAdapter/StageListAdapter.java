package haur.haurrankingandroid.activity.listAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.ClassifierSetup;
import haur.haurrankingandroid.domain.ClassifierSetupObject;
import haur.haurrankingandroid.domain.StageListItem;
import haur.haurrankingandroid.service.classifier.ClassifierSetupInfoService;

/**
 * Created by Jarno on 25.10.2018.
 */

public class StageListAdapter extends ArrayAdapter<StageListItem> {
	private final Context context;
	private final List<StageListItem> stageList;

	public StageListAdapter(Context context, List<StageListItem> stageList) {
		super(context, -1, stageList);
		this.context = context;
		this.stageList = stageList;
	}

	private static class ViewHolder {
		private TextView stageName;
		private Spinner classifierSpinner;
		private CheckBox selectStageCheckBox;

	}
	@NonNull
	@Override
	public View getView(final int position, View recycledView, @NonNull ViewGroup parent) {
		ViewHolder viewHolder;
		if (recycledView == null || recycledView.getTag() == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			viewHolder = new ViewHolder();

			recycledView = inflater.inflate(R.layout.stage_list_item, parent, false);
			viewHolder.stageName = (TextView) recycledView.findViewById(R.id.stage_name);
			viewHolder.classifierSpinner = (Spinner) recycledView.findViewById(R.id.classifier_spinner);
			viewHolder.selectStageCheckBox = (CheckBox) recycledView.findViewById(R.id.select_stage_checkbox);
			recycledView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) recycledView.getTag();
		}

		final StageListItem item = stageList.get(position);
		viewHolder.stageName.setText(item.getStage().getName());
		viewHolder.selectStageCheckBox.setChecked(item.isSelected());
		viewHolder.selectStageCheckBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				item.setSelected(((CheckBox) v).isChecked());
			}
		});

		List<Classifier> classifierOptions = getClassifierOptions(item);
		ArrayAdapter<Classifier> spinnerAdapter = new ArrayAdapter<>(getContext(),
				android.R.layout.simple_spinner_item, classifierOptions);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		viewHolder.classifierSpinner.setAdapter(spinnerAdapter);
		if (item.getClassifier() != null) {
			viewHolder.classifierSpinner.setSelection(classifierOptions.indexOf(item.getClassifier()), false);
			Log.i("ADAPTER", "Adapter setting classifier spinner to " + item.getClassifier());
		}

		viewHolder.classifierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				item.setClassifier(classifierOptions.get(position));
				Log.i("ADAPTER", "CHANGED CLASSIFIER TO " + item.getClassifier() + " FOR " + item.getStage().getName());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});




		return recycledView;
	}

	// Returns a list of classifiers corresponding to min rounds count and target count of
	// the stage in the classifiersSpinner list.
	private List<Classifier> getClassifierOptions(final StageListItem item) {
		ClassifierSetupObject classifierSetupData = ClassifierSetupInfoService.getClassifierSetupObject();
		List<Classifier> classifierOptions = new ArrayList<>();
		for (ClassifierSetup classifierSetup : classifierSetupData.getClassifierSetups()) {
			if (classifierSetup.getMinRounds() == item.getStage().getMinRounds()
					&& classifierSetup.getPaperTargets() == item.getStage().getTargets().length
			&& classifierSetup.getSteelTargets() == item.getStage().getPoppers()) {
				classifierOptions.add(classifierSetup.getClassifier());
			}
		}
		return classifierOptions;
	}
}
