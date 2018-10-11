package haur.haurrankingandroid;

/**
 * Created by Jarno on 11.10.2018.
 */

public class Constants {
	public static final String NOTIFICATION_CHANNEL_ID = "PRACTI_SCORE_UPLOADER_NOTIFICATION_CHANNEL";

	public enum NOTIFICATION_TYPE { LOUD, DISCREET; }

	public interface ACTION {
		public static String MAIN_ACTION = "com.nkdroid.alertdialog.action.main";
		public static String STARTFOREGROUND_ACTION = "com.nkdroid.alertdialog.action.startforeground";
		public static String STOPFOREGROUND_ACTION = "com.nkdroid.alertdialog.action.stopforeground";
	}

	public interface NOTIFICATION_ID {
		public static int FOREGROUND_SERVICE = 101;
	}
}
