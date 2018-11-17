package haur.haurrankingandroid.domain;

import android.util.Log;

/**
 * Created by Jarno on 25.10.2018.
 */

public class StageListItem {
	private Stage stage;
	private boolean selected;
	private Classifier classifier;

	public StageListItem(Stage stage) {
		this.stage = stage;
		if (stage != null && stage.getClassifierCode() != null && stage.getClassifierCode().length() > 0) {
			classifier = Classifier.fromPractiScoreCode(stage.getClassifierCode());
			selected = true;
		}
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}
}
