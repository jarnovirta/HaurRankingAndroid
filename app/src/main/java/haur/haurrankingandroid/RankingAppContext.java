package haur.haurrankingandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Jarno on 11.10.2018.
 */

public class RankingAppContext extends Application {
	private static Context context;

	public void onCreate() {
		Log.d("CONTEXT", "oncreate called");
		super.onCreate();
		RankingAppContext.context = getApplicationContext();
	}

	public static Context getAppContext() {
		return RankingAppContext.context;
	}
}
