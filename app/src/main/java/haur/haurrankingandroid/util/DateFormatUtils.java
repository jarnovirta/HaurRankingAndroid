package haur.haurrankingandroid.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jarno on 14.10.2018.
 */

public class DateFormatUtils {
	public static String dateToString(Date date) {
		if (date == null)
			return "";
		String dateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		try {
			dateString = sdf.format(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}

	public static Date stringToDate(String dateString) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			date = sdf.parse(dateString);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
}
