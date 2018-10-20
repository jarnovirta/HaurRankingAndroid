package haur.haurrankingandroid.data.dao.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import haur.haurrankingandroid.domain.Classifier;

/**
 * Created by Jarno on 13.10.2018.
 */

public class ClassifierConverter {
	@TypeConverter
	public static Classifier toClassifier(String statusString) {
		return Classifier.fromString(statusString);
	}

	@TypeConverter
	public static String toString(Classifier classifier) {
		return classifier.toString();
	}
}