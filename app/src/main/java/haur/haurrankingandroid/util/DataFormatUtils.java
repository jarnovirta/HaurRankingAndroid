package haur.haurrankingandroid.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jarno on 18.10.2018.
 */

public class DataFormatUtils {
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
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
