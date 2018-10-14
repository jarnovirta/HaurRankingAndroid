package haur.haurrankingandroid.domain;

import android.util.Log;

/**
 * Created by Jarno on 13.10.2018.
 */

// IPSC classification stage listing.
// http://www.ipsc.org/classification/icsStages.php
public enum Classifier {CLC01, CLC03, CLC05, CLC07, CLC09, CLC11, CLC13, CLC15, CLC17, CLC19, CLC21, CLC23, CLC25, CLC27, CLC29, CLC31, CLC33, CLC35, CLC37, CLC39, CLC41, CLC43, CLC45, CLC47, CLC49, CLC51, CLC53, CLC55, CLC57, CLC59, CLC61, CLC63, CLC65, CLC67, CLC69, CLC71, CLC73, CLC75, CLC77, CLC79;

		public static boolean contains(String testString) {
			for (Classifier stage : Classifier.values()) {
				if (stage.toString().equals(testString))
					return true;
			}
			return false;
		}

		public static Classifier getClassifierByWinMSSStandardStageType(int winMSSStandardStageType) {
			String typeString = String.valueOf(winMSSStandardStageType);
			if (typeString.length() == 1) {
				typeString = "0" + typeString;
			}

			for (Classifier classifier : Classifier.values()) {
				String stageString = classifier.toString();
				if (stageString.substring(4, stageString.length()).equals(typeString)) {
					return classifier;
				}
			}
			return null;
		}

		public static Classifier fromPractiScoreCode(String practiScoreCode) {
			if (practiScoreCode.length() < 4) return null;
			return fromString("CLC-" + practiScoreCode.split("-")[1]);
		}
		public static Classifier fromString(String classifierString) {
			for (Classifier classifier : Classifier.values()) {
				if (classifier.toString().equals(classifierString))
					return classifier;
			}
			return null;
		}

		@Override
		public String toString() {
			return this.name().substring(0, 3) + "-" + this.name().substring(3);
		}
}

