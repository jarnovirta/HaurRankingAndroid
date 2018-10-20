package haur.haurrankingandroid.service.test;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import haur.haurrankingandroid.domain.Competitor;
import haur.haurrankingandroid.domain.Division;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.ScoreCard;
import haur.haurrankingandroid.util.DataFormatUtils;

/**
 * Created by Jarno on 14.10.2018.
 */

public class TestDataGenerator {
	static String[] classifiers = new String[] { "CLC-01", "CLC-03", "CLC-05", "CLC-07", "CLC-09",
			"CLC-11", "CLC-13", "CLC-15", "CLC-17", "CLC-19"};

	static Competitor jarno = new Competitor("Jarno", "Virta", "jarnoPractiId");
	static Competitor jerry = new Competitor("Jerry", "Miculek", "jerryPractiId");
	static Competitor ben = new Competitor("Ben", "Stoeger", "benPractiId");
	static Competitor rob = new Competitor("Rob", "Leatham", "robPractiId");
	static Competitor clint = new Competitor("Clint", "Eastwood", "clintPractiId");
	static Competitor charles = new Competitor("Charles", "Bronson", "charlesPractiId");


	static Double[] jarnoNewMatchHitFactors = new Double[] { 4.0, 3.9, 2.0, null, 5.5, 4.0, 4.5, null, null, 1.0 };

	static Double[] jerryNewMatchHitFactors = new Double[] { 3.1, null, null, 3.9, null, 2.3, 3.9, null, null, 1.5 };

	static Double[] benNewMatchHitFactors = new Double[] { 1.5, 4.1, 2.9, 0.0, 4.8, null, null, null, null, 2.1 };

	static Double[] robNewMatchHitFactors = new Double[] { 2.0, 2.2, 3.6, null, null, null, null, null, 5.2, null };

	static Double[] jarnoOldMatchHitFactors = new Double[] { null, null, null, null, 6.0, null, null, 3.6, null, null };

	static Double[] jerryOldMatchHitFactors = new Double[] { null, null, null, 4.2, null, null, null, 2.5, null, null };

	static Double[] jarnoSpringMatchHitFactors = new Double[] { null, null, null, 5.1, null, null, null, null, null,
			null };

	static Double[] clintSpringMatchHitFactors = new Double[] { 3.5, 4.5, 2.5, 5.5, null, null, null, null, 4.5, null };

	static Double[] charlesSpringMatchHitFactors = new Double[] { 2.2, 5.8, 6.0, 5.7, null, null, null, null, null,
			null };

	public static List<Match> generateTestData() {
		Log.d("TEST GENERATOR", "GENERATING TEST DATA...");
		List<Match> matches = getTestMatches();
		try {
			for (Match match : matches) {
				match.setScoreCards(createScoreCards(match));
			}
		}
		catch (Exception e) {
			Log.e("TEST GENERATOR", e.getMessage(), e);
		}
		return matches;
	}


	public static List<Match> getTestMatches() {
		List<Match> matches = new ArrayList<Match>();
		matches.add(createNewTestMatch());
		matches.add(createOldTestMatch());
		matches.add(createSpringTestMatch());
		return matches;
	}

	protected static Match createNewTestMatch() {
		Match newTestMatch = new Match();
		newTestMatch.setName("New match");
		newTestMatch.setPractiScoreId("newMatchPSId");
		newTestMatch.setDate(DataFormatUtils.stringToDate("18.11.2017"));
		setCompetitorsToMatch(newTestMatch);
		return newTestMatch;
	}

	protected static Match createOldTestMatch() {
		Match oldTestMatch = new Match();
		oldTestMatch.setName("Old match");
		oldTestMatch.setPractiScoreId("oldMatchPSId");
		oldTestMatch.setDate(DataFormatUtils.stringToDate("12.11.2017"));
		setCompetitorsToMatch(oldTestMatch);
		return oldTestMatch;
	}
	protected static Match createSpringTestMatch() {
		Match springTestMatch = new Match();
		springTestMatch.setName("Spring match");
		springTestMatch.setPractiScoreId("springMatchPSId");
		springTestMatch.setDate(DataFormatUtils.stringToDate("01.04.2017"));
		setCompetitorsToMatch(springTestMatch);
		return springTestMatch;
	}

	protected static void setCompetitorsToMatch(Match match) {
		List<Competitor> comps = new ArrayList<Competitor>();
		comps.add(jarno);
		comps.add(jerry);
		comps.add(ben);
		comps.add(rob);
		comps.add(clint);
		comps.add(charles);
		match.setCompetitors(comps);
	}
	public static List<ScoreCard> createScoreCards(Match match) {
		List<ScoreCard> sheets = new ArrayList<ScoreCard>();
		int index = 0;
		for (String classifier : classifiers) {

			if (match.getName().equals("New match")) {
				if (jarnoNewMatchHitFactors[index] != null)
					sheets.add(new ScoreCard(jarno, classifiers[index], jarnoNewMatchHitFactors[index],
							getCompetitorDivision(jarno)));
				if (jerryNewMatchHitFactors[index] != null)
					sheets.add(new ScoreCard(jerry, classifiers[index], jerryNewMatchHitFactors[index], getCompetitorDivision(jerry)));
				if (benNewMatchHitFactors[index] != null)
					sheets.add(new ScoreCard(ben, classifiers[index], benNewMatchHitFactors[index], getCompetitorDivision(ben)));
				if (robNewMatchHitFactors[index] != null)
					sheets.add(new ScoreCard(rob, classifiers[index], robNewMatchHitFactors[index], getCompetitorDivision(rob)));
			}
			if (match.getName().equals("Old match")) {
				if (jarnoOldMatchHitFactors[index] != null)
					sheets.add(new ScoreCard(jarno, classifiers[index], jarnoOldMatchHitFactors[index], getCompetitorDivision(jarno)));
				if (jerryOldMatchHitFactors[index] != null)
					sheets.add(new ScoreCard(jerry, classifiers[index], jerryOldMatchHitFactors[index], getCompetitorDivision(jerry)));
			} else {
				if (match.getName().equals("Spring match")) {
					if (jarnoSpringMatchHitFactors[index] != null)
						sheets.add(new ScoreCard(jarno, classifiers[index], jarnoSpringMatchHitFactors[index], getCompetitorDivision(jarno)));
					if (clintSpringMatchHitFactors[index] != null)
						sheets.add(new ScoreCard(clint, classifiers[index], clintSpringMatchHitFactors[index], getCompetitorDivision(clint)));
					if (charlesSpringMatchHitFactors[index] != null) {
						sheets.add(new ScoreCard(charles, classifiers[index], charlesSpringMatchHitFactors[index], getCompetitorDivision(charles)));
					}

				}
			}
			index++;
		}
		return sheets;
	}
	protected static Division getCompetitorDivision(Competitor comp) {
		switch (comp.getLastName()) {
			case "Bronson":
			case "Eastwood":
				return Division.STANDARD;
			default:
				return Division.PRODUCTION;
		}
	}
}
