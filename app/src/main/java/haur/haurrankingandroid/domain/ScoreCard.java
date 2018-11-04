package haur.haurrankingandroid.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreCard implements Comparable<ScoreCard> {

	@PrimaryKey(autoGenerate = true)
	private Long id;

	private Long matchId;

	private Long competitorId;

	private Classifier classifier;

	private Division division;


//	The rest of the fields are not persisted:

	@Ignore
	private Match match;

	@JsonProperty("stage_uuid")
	@Ignore
	private String stagePractiScoreId;

	@JsonProperty("shtr")
	@Ignore
	private String competitorPractiScoreId;

	@Ignore
	private Competitor competitor;

	@JsonProperty("popm")
	@Ignore
	private int popperMisses;

	@JsonProperty("poph")
	@Ignore
	private int popperHits;

	@JsonProperty("popns")
	@Ignore
	private int popperNoshootHits;

	@JsonProperty("popnpm")
	@Ignore
	private int popperNonPenaltyMisses;

	@JsonProperty("rawpts")
	@Ignore
	private int points;

	@JsonProperty("str")
	@Ignore
	private double[] stringTimes;

	@Ignore
	private double time;

	@JsonProperty("ts")
	@Ignore
	private int[] paperTargetHits = {};

	@JsonProperty("dnf")
	@Ignore
	private boolean dnf;

	@Ignore
	private int aHits;

	@Ignore
	private int cHits;

	@Ignore
	private int dHits;

	@Ignore
	private int misses;

	@Ignore
	private int noshootHits;

	@JsonProperty("proc")
	@Ignore
	private int proceduralPenalties;

	@JsonProperty("apen")
	@Ignore
	private int additionalPenalties;

	private double hitFactor;

	public ScoreCard() { }

	public ScoreCard(Competitor competitor, Classifier classifier, double hitfactor, Division division) {
		this.competitor = competitor;
		this.competitorPractiScoreId = competitor.getPractiScoreId();
		this.classifier = classifier;
		this.hitFactor = hitfactor;
		this.division = division;
	}

	@Override
	public int compareTo(ScoreCard compareToScoreCard) {
		final int EQUAL = 0;
		if (this == compareToScoreCard) return EQUAL;
		return new Double(compareToScoreCard.getHitFactor()).compareTo(new Double(this.hitFactor));
	}

	public String getCompetitorPractiScoreId() {
		return competitorPractiScoreId;
	}

	public void setCompetitorPractiScoreId(String competitorPractiScoreId) {
		this.competitorPractiScoreId = competitorPractiScoreId;
	}

	public int getPopperMisses() {
		return popperMisses;
	}

	public void setPopperMisses(int popperMisses) {
		this.popperMisses = popperMisses;
	}

	public int getPopperHits() {
		return popperHits;
	}

	public void setPopperHits(int popperHits) {
		this.popperHits = popperHits;
	}

	public int getPopperNonPenaltyMisses() {
		return popperNonPenaltyMisses;
	}

	public void setPopperNonPenaltyMisses(int popperNonPenaltyMisses) {
		this.popperNonPenaltyMisses = popperNonPenaltyMisses;

	}
	public int getPopperNoshootHits() {
		return popperNoshootHits;
	}
	public void setPopperNoshootHits(int popperNoshootHits) {
		this.popperNoshootHits = popperNoshootHits;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public double getTime() {
		return time;
	}

	public int[] getPaperTargetHits() {
		return paperTargetHits;
	}

	public void setPaperTargetHits(int[] paperTargetHits) {
		this.paperTargetHits = paperTargetHits;

	}

	public void setHitsAndPoints() {

		if (dnf) {
			points = 0;
			return;
		}

		aHits = 0;
		cHits = 0;
		dHits = 0;
		noshootHits = 0;
		misses = 0;

		// Count total hits from PractiScore hits data
		int cHitsBitShift = 8;
		int dHitsBitShift = 12;
		int noshootHitsBitShift = 16;
		int missesBitShift = 20;

		int bitMask = 0xF;

		for (int hits : paperTargetHits) {
			this.aHits += hits & bitMask;
			this.cHits += (hits >> cHitsBitShift) & bitMask;
			this.dHits += (hits >> dHitsBitShift) & bitMask;
			this.noshootHits += (hits >> noshootHitsBitShift) & bitMask;
			this.misses += (hits >> missesBitShift) & bitMask;
		}

		this.aHits += this.popperHits;
		this.misses += popperMisses;
		this.noshootHits += popperNoshootHits;

		this.points -= noshootHits * 10;
		this.points -= misses * 10;
		this.points -= proceduralPenalties * 10;
		this.points -= additionalPenalties;

		if (this.points >=0 && this.time > 0 && competitor.getPowerFactor() != null
				&& !competitor.getPowerFactor().equals("SUBMINOR")) {
			this.hitFactor = this.points / this.time;
		}
		else {
			this.points = 0;
			this.hitFactor = 0;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public Long getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(Long competitorId) {
		this.competitorId = competitorId;
	}

	public double[] getStringTimes() {
		return stringTimes;
	}

	public void setStringTimes(double[] stringTimes) {
		if (stringTimes != null && stringTimes.length > 0) {
			this.time = stringTimes[0];
		}
	}

	public void setTime(double time) {
		this.time = time;
	}

	public boolean isDnf() {
		return dnf;
	}

	public void setDnf(boolean dnf) {
		this.dnf = dnf;
	}

	public int getAHits() {
		return aHits;
	}

	public void setAHits(int aHits) {
		this.aHits = aHits;
	}

	public int getCHits() {
		return cHits;
	}

	public void setCHits(int cHits) {
		this.cHits = cHits;
	}

	public int getDHits() {
		return dHits;
	}

	public void setDHits(int dHits) {
		this.dHits = dHits;
	}

	public int getMisses() {
		return misses;
	}

	public void setMisses(int misses) {
		this.misses = misses;
	}

	public int getNoshootHits() {
		return noshootHits;
	}

	public void setNoshootHits(int noshootHits) {
		this.noshootHits = noshootHits;
	}

	public int getProceduralPenalties() {
		return proceduralPenalties;
	}

	public void setProceduralPenalties(int proceduralPenalties) {
		this.proceduralPenalties = proceduralPenalties;
	}

	public int getAdditionalPenalties() {
		return additionalPenalties;
	}

	public void setAdditionalPenalties(int additionalPenalties) {
		this.additionalPenalties = additionalPenalties;
	}

	public double getHitFactor() {
		return hitFactor;
	}

	public void setHitFactor(double hitFactor) {
		this.hitFactor = hitFactor;
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public String getStagePractiScoreId() {
		return stagePractiScoreId;
	}

	public void setStagePractiScoreId(String stagePractiScoreId) {
		this.stagePractiScoreId = stagePractiScoreId;
	}
}