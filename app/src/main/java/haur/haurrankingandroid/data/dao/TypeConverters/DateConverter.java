package haur.haurrankingandroid.data.dao.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Jarno on 13.10.2018.
 */

public class DateConverter {

		@TypeConverter
		public static Date toDate(Long timestamp) {
			return timestamp == null ? null : new Date(timestamp);
		}

		@TypeConverter
		public static Long toTimestamp(Date date) {
			return date == null ? null : date.getTime();
		}
}
