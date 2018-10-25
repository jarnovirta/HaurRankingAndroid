package haur.haurrankingandroid.service.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

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

	public SaveMatchTask(SaveMatchTaskResponseHandler handler) {
		this.handler = handler;
	}
	@Override
	protected Void doInBackground(Match... params) {
		try {
			for (Match match : params) {
				AppDatabase db = AppDatabase.getDatabase();
				match.setId(db.matchDao().insert(match));
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
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		if (handler != null) handler.process();
	}

	private static void saveScoreCards(Match match) {
		AppDatabase db = AppDatabase.getDatabase();
		for (ScoreCard card : match.getScoreCards()) {
			card.setCompetitorId(card.getCompetitor().getId());
			card.setMatchId(match.getId());
			// Delete old ScoreCard for the same competitor and classifier
			db.scoreCardDao().deleteOldScoreCard(card.getCompetitorId(), card.getClassifier());
		}
		db.scoreCardDao().insertAll(match.getScoreCards());
	}
	private static void saveCompetitors(List<Competitor> competitors, Long matchId) {
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
}
