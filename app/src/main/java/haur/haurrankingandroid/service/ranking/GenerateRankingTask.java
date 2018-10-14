package haur.haurrankingandroid.service.ranking;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import haur.haurrankingandroid.data.AppDatabase;
import haur.haurrankingandroid.domain.Division;
import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.domain.ScoreCard;

/**
 * Created by Jarno on 14.10.2018.
 */

public class GenerateRankingTask extends AsyncTask<Void, Void, Ranking> {

	private GenerateRankingResponseHandler handler;

	public GenerateRankingTask(GenerateRankingResponseHandler handler) {
		this.handler = handler;
	}
	@Override
	protected Ranking doInBackground(Void... args) {
		Ranking ranking = new Ranking();
		AppDatabase db = AppDatabase.getDatabase();

		for (Division division : Division.values()) {
			Log.d("TEST", "\n\n\t**** DIVISION: " + division.toString() + " **** ");
			DivisionRanking divisionRanking = new DivisionRanking();
			List<ScoreCard> scoreCards = db.scoreCardDao().findForValidClassifiers(division);
			setMatchesToCards(scoreCards);
			Map<Long, List<ScoreCard>> scoreCardMap = mapCardsByCompetitor(scoreCards);
			sortAndFilterScoreCardsMap(scoreCardMap);
			for (Long id : scoreCardMap.keySet()) {
				Log.d("TEST", "\n\n**** COMPETITOR ID: " + id + ": CARDS COUNT : " + scoreCardMap.get(id).size());
			}
		}
		return null;
	}

	private void sortAndFilterScoreCardsMap(Map<Long, List<ScoreCard>> scoreCardsMap) {
		Set<Long> removeIds = new HashSet<>();
		for (Long id : scoreCardsMap.keySet()) {
			List<ScoreCard> cards = scoreCardsMap.get(id);
			if (cards.size() < 4) {
				removeIds.add(id);
				continue;
			}
			Collections.sort(cards, new ScoreCardsByDateComparator());
			Collections.reverse(cards);
			if (cards.size() > 4) scoreCardsMap.put(id, cards.subList(0, 4));
		}
		scoreCardsMap.keySet().removeAll(removeIds);
	}

	public class ScoreCardsByDateComparator implements Comparator<ScoreCard> {
		@Override
		public int compare(ScoreCard o1, ScoreCard o2) {
			return o1.getMatch().getDate().compareTo(o2.getMatch().getDate());
		}
	}
	private void setMatchesToCards(List<ScoreCard> cards) {
		AppDatabase db = AppDatabase.getDatabase();

		List<Long> matchIds = new ArrayList<>();
		for (ScoreCard card : cards) {
			if (!matchIds.contains(card.getMatchId())) matchIds.add(card.getMatchId());
		}
		List<Match> matches = db.matchDao().find(matchIds);
		for (ScoreCard card : cards) {
			for (Match match : matches) {
				if (card.getMatchId().equals(match.getId())) {
					card.setMatch(match);
				}
			}
		}
	}


	private Map<Long, List<ScoreCard>> mapCardsByCompetitor(List<ScoreCard> cards) {
		Map<Long, List<ScoreCard>> result = new HashMap<>();
		for (ScoreCard card : cards) {
			if (!result.keySet().contains(card.getCompetitorId())) {
				List<ScoreCard> competitorCards = new ArrayList<>();
				competitorCards.add(card);
				result.put(card.getCompetitorId(), competitorCards);
			}
			else {
				result.get(card.getCompetitorId()).add(card);
			}
		}
		for (List<ScoreCard> competitorCards : result.values()) {

		}
		return result;
	}

	@Override
	protected void onPostExecute(Ranking ranking) {

		if (handler != null) handler.process(ranking);
	}
}

// Loop divisions

// Get classifiers with min 2 results and average of top two hitfactors

// Get competitors with min 4 results in division for valid classifiers. Get max 8
// latest result cards, order by match date.

// Loop competitors
// and calculate relative classifier results. Get average of best 4.
// Get DivisionRankingRow for competitor with average result.

// Order rows by score average.

// Set percentages etc.
//
