package haur.haurrankingandroid.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarno on 20.10.2018.
 */

public class AppEventService {

	private static List<AppEventListener> listeners = new ArrayList<>();

	public static void addListener(AppEventListener listener) {
		listeners.add(listener);
	}

	public static void emit(AppEvent event) {
		for (AppEventListener listener : listeners) {
			listener.process(event);
		}
	}
}
