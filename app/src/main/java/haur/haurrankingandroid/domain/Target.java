package haur.haurrankingandroid.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jarno on 27.1.2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class Target {
	@JsonProperty("target_number")
	private int targetNumber;

	@JsonProperty("target_reqshots")
	private int requiredShots;


	public int getRequiredShots() {
		return requiredShots;
	}

	public void setRequiredShots(int requiredShots) {
		this.requiredShots = requiredShots;
	}

	public int getTargetNumber() {
		return targetNumber;
	}

	public void setTargetNumber(int targetNumber) {
		this.targetNumber = targetNumber;
	}
}