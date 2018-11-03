package haur.haurrankingandroid.service.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import haur.haurrankingandroid.RankingAppContext;
import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.ScoreCard;
import haur.haurrankingandroid.service.task.onPostExecuteHandler.SaveMatchTaskResponseHandler;

/**
 * Created by Jarno on 14.10.2018.
 */

public class SaveMatchTask extends AsyncTask<Match, Void, Void> {

	private SaveMatchTaskResponseHandler handler;
	private Activity activity;

	public SaveMatchTask(SaveMatchTaskResponseHandler handler, Activity activity) {
		this.handler = handler;
		this.activity = activity;
	}
	@Override
	protected Void doInBackground(Match... params) {
		try {
			AppDatabase db = AppDatabase.getDatabase();
			for (Match match : params) {
				Match oldMatch = db.matchDao().findByNameAndDate(match.getName(), match.getDate());
				if (oldMatch == null) {
					match.setId(db.matchDao().insert(match));
				}
				else match.setId(oldMatch.getId());
				if (match.getCompetitors() != null && match.getCompetitors().size() > 0) {
					saveCompetitors(match.getCompetitors(), match.getId());
				}

				if (match.getScoreCards() != null && match.getScoreCards().size() > 0) {
					saveScoreCards(match);
				}
			}
		}
		catch (Exception e) {
			Log.e("SaveDataTask", e.getMessage(), e);
			showToastMessage("Tapahtui virhe!");

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		showToastMessage("Tulokset tallennettu!");

		if (handler != null) handler.process();
	}

	private void saveScoreCards(Match match) {
		AppDatabase db = AppDatabase.getDatabase();
		for (ScoreCard card : match.getScoreCards()) {
			card.setCompetitorId(card.getCompetitor().getId());
			card.setMatchId(match.getId());
			// Delete old ScoreCard for the same competitor and classifier
			db.scoreCardDao().deleteOldScoreCard(card.getCompetitorId(), card.getClassifier());
		}
		db.scoreCardDao().insertAll(match.getScoreCards());
	}
	private void saveCompetitors(List<Competitor> competitors, Long matchId) {
		AppDatabase db = AppDatabase.getDatabase();

		for (Competitor comp : competitors) {
			Competitor existingCompetitor = db.competitorDao().findByName(comp.getFirstName(),
					comp.getLastName());
			if (existingCompetitor != null) comp.setId(existingCompetitor.getId());
			else {
				comp.setId(db.competitorDao().insert(comp));
			}
		}
	}

	private void showToastMessage(String message) {
		if (activity != null) activity.runOnUiThread(() -> {
			Toast toast = Toast.makeText(activity,
					message,
					Toast.LENGTH_SHORT);
			toast.show();
		});
	}
}
