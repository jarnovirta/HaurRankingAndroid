package haur.haurrankingandroid.domain;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
public class MatchScore implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("match_id")
	private String matchId;
	@JsonProperty("match_scores")
	private List<StageScore> stageScores;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public List<StageScore> getStageScores() {
		return stageScores;
	}

	public void setStageScores(List<StageScore> stageScores) {
		this.stageScores = stageScores;
	}

}