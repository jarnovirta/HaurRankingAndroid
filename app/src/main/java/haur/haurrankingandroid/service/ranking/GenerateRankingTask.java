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
import haur.haurrankingandroid.domain.Classifier;
import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Division;
import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.DivisionRankingRow;
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
		ranking.setDivisionRankings(new ArrayList<DivisionRanking>());

		AppDatabase db = AppDatabase.getDatabase();

		for (Division division : Division.values()) {
			Log.d("TEST", "\n\n\t**** DIVISION: " + division.toString() + " **** ");

			List<ScoreCard> scoreCards = db.scoreCardDao().findForValidClassifiers(division);
			setMatchesToCards(scoreCards);

			Map<Competitor, List<ScoreCard>> scoreCardMap = mapCardsByCompetitor(scoreCards);
			sortAndFilterScoreCardsMap(scoreCardMap);

			ranking.getDivisionRankings().add(generateDivisionRanking(division, scoreCardMap));

		}
		return null;
	}

	private DivisionRanking generateDivisionRanking(Division division,
	                                                Map<Competitor, List<ScoreCard>> scoreCardMap) {

		DivisionRanking divisionRanking = new DivisionRanking();

		// Scorecards by classifier
		Map<Classifier, List<ScoreCard>> classifierScoreCardsMap = new HashMap<>();

		// Average of top two hitfactors for a classifier
		Map<Classifier, Double> classifierTopHitFactorsAveragesMap;

		// Competitor relative result for classifier mapped by ScoreCard
		Map<Competitor, List<Double>> competitorRelativeResultsMap = new HashMap<>();

		for (List<ScoreCard> cards : scoreCardMap.values()) {
			for (ScoreCard card : cards) {
				if (!classifierScoreCardsMap.keySet().contains(card.getClassifier())) {
					classifierScoreCardsMap.put(card.getClassifier(), new ArrayList<ScoreCard>());
				}
				classifierScoreCardsMap.get(card.getClassifier()).add(card);
			}
		}

		classifierTopHitFactorsAveragesMap = getClassifierTopHitFactorsAverageMap(classifierScoreCardsMap);

		// Map relative results for competitors
		for (Classifier classifier : classifierScoreCardsMap.keySet()) {
			for (ScoreCard card : classifierScoreCardsMap.get(classifier)) {
				double topTwoAverage = classifierTopHitFactorsAveragesMap.get(classifier);
				double relativeResult;
				if (topTwoAverage > 0) {
					relativeResult = card.getHitFactor() / topTwoAverage;
				}
				else relativeResult = 0;
				if (!competitorRelativeResultsMap.keySet().contains(card.getCompetitor())) {
					competitorRelativeResultsMap.put(card.getCompetitor(), new ArrayList<Double>);
				}
				competitorRelativeResultsMap.get(card.getCompetitor()).add(relativeResult);
			}
		}
		// Generate division ranking rows for competitors
		List<DivisionRankingRow> rows = new ArrayList<>();
		for (Competitor comp : competitorRelativeResultsMap.keySet()) {
			List<Double> relativeResults = competitorRelativeResultsMap.get(comp);

			Collections.sort(relativeResults);
			int resultsCount = relativeResults.size();
			if (relativeResults.size() > 4) relativeResults = relativeResults.subList(0, 4);

			double sum = 0.0;
			double average = 0;
			for (double result : relativeResults) sum += result;

			if (relativeResults.size() > 0) {
				average = sum / relativeResults.size();
			}
			rows.add(new DivisionRankingRow(comp, average, resultsCount));
		}
		return divisionRanking;

	}

	private Map<Classifier, Double> getClassifierTopHitFactorsAverageMap(Map<Classifier,
			List<ScoreCard>> classifierScoreCardsMap) {

		Map<Classifier, Double> result = new HashMap<>();

		for (Classifier classifier : classifierScoreCardsMap.keySet()) {
			List<ScoreCard> cards = classifierScoreCardsMap.get(classifier);
			Collections.sort(cards);
			Double topTwoAverage = null;
			topTwoAverage = cards.get(0).getHitFactor() + cards.get(1).getHitFactor() / 100;

			result.put(classifier, topTwoAverage);
		}
		return result;
	}

	private void sortAndFilterScoreCardsMap(Map<Competitor, List<ScoreCard>> scoreCardsMap) {
		for (Competitor comp : scoreCardsMap.keySet()) {
			List<ScoreCard> cards = scoreCardsMap.get(comp);

			Collections.sort(cards, new ScoreCardsByDateComparator());
			Collections.reverse(cards);
			if (cards.size() > 8) scoreCardsMap.put(comp, cards.subList(0, 8));
		}
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


	private Map<Competitor, List<ScoreCard>> mapCardsByCompetitor(List<ScoreCard> cards) {
		AppDatabase db = AppDatabase.getDatabase();
		Set<Long> ids = new HashSet<>();
		for (ScoreCard card : cards)  if (!ids.contains(card.getCompetitorId())) {
			ids.add(card.getCompetitorId());
		}
		List<Competitor> competitors = db.competitorDao().find(ids);

		Map<Competitor, List<ScoreCard>> result = new HashMap<>();

		for (ScoreCard card : cards) {
			Competitor competitor = getCompetitor(competitors, card.getCompetitorId());

			if (!result.keySet().contains(card.getCompetitor())) {
				result.put(competitor, new ArrayList<ScoreCard>());

			}
			result.get(card.getCompetitor()).add(card);

		}
		return result;
	}

	private Competitor getCompetitor(List<Competitor> competitors, Long id) {
		for (Competitor comp : competitors) if (comp.getId().equals(id)) return comp;
		return null;
	}
	@Override
	protected void onPostExecute(Ranking ranking) {

		if (handler != null) handler.process(ranking);
	}
}
