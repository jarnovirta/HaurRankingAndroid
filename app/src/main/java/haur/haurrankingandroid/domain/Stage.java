package haur.haurrankingandroid.domain;

import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stage {

	@PrimaryKey(autoGenerate = true)
	private Long id;

	@JsonProperty("stage_uuid")
	private String practiScoreId;

	private Long matchId;

	@JsonIgnore
	private Match match;

	@JsonProperty("stage_name")
	private String name;

	@JsonProperty("stage_scoretype")
	private String scoreType;

	@JsonProperty("stage_number")
	private int stageNumber;

	@JsonProperty("stage_targets")
	private Target[] targets;

	@JsonProperty("stage_poppers")
	private int poppers;

	private int maxPoints;

	@JsonProperty("stage_deleted")
	private boolean deleted = false;

	@JsonProperty("stage_classifiercode")
	private String classifierCode;

	private int minRounds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPractiScoreId() {
		return practiScoreId;
	}

	public void setPractiScoreId(String practiScoreId) {
		this.practiScoreId = practiScoreId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Target[] getTargets() {
		return targets;
	}

	public void setTargets(Target[] targets) {
		this.targets = targets;
		setRoundCountAndMaxPoints();
	}

	public int getPoppers() {
		return poppers;
	}

	public void setPoppers(int poppers) {
		this.poppers = poppers;
		setRoundCountAndMaxPoints();
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public void setRoundCountAndMaxPoints() {
		this.maxPoints = 0;
		minRounds = poppers;
		if (targets != null && targets.length > 0) {
			for (Target target : targets) {
				maxPoints += target.getRequiredShots() * 5;
				minRounds += target.getRequiredShots();
			}
		}
		maxPoints += poppers * 5;

	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setRoundCountAndMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public String getClassifierCode() {
		return classifierCode;
	}

	public void setClassifierCode(String classifierCode) {
		this.classifierCode = classifierCode;
	}

	public int getMinRounds() {
		return minRounds;
	}
}