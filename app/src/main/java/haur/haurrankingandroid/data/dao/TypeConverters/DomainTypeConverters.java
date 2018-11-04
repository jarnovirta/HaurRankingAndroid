package haur.haurrankingandroid.data.dao.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Division;

/**
 * Created by Jarno on 4.11.2018.
 */

public class DomainTypeConverters {
	@TypeConverter
	public static Division toDivision(String divisionString) {
		return Division.fromString(divisionString);
	}

	@TypeConverter
	public static String toString(Division division) {
		return division.toString();
	}
	@TypeConverter
	public static Classifier toClassifier(String statusString) {
		return Classifier.fromString(statusString);
	}

	@TypeConverter
	public static String toString(Classifier classifier) {
		return classifier.toString();
	}
	@TypeConverter
	public static Date toDate(Long timestamp) {
		return timestamp == null ? null : new Date(timestamp);
	}

	@TypeConverter
	public static Long toTimestamp(Date date) {
		return date == null ? null : date.getTime();
	}
}
