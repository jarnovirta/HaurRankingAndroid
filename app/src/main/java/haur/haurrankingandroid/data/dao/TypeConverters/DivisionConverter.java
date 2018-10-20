package haur.haurrankingandroid.data.dao.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import haur.haurrankingandroid.domain.Division;

/**
 * Created by Jarno on 13.10.2018.
 */

public class DivisionConverter {
	@TypeConverter
	public static Division toDivision(String divisionString) {
		return Division.fromString(divisionString);
	}

	@TypeConverter
	public static String toString(Division division) {
		return division.toString();
	}
}
