package haur.haurrankingandroid.domain;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Competitor implements Comparable<Competitor> {

	@PrimaryKey(autoGenerate = true)
	private Long id;

	@JsonProperty("sh_fn")
	private String firstName;

	@JsonProperty("sh_ln")
	private String lastName;


//	Following fields not persisted:

	@Ignore
	private Division division;

	@JsonProperty("sh_uid")
	@Ignore
	private String practiScoreId;

	@JsonProperty("sh_dvp")
	@Ignore
	private String practiScoreDivisionString;

	@JsonProperty("sh_dq")
	@Ignore
	private boolean disqualified;

	@JsonProperty("sh_pf")
	@Ignore
	private String practiScorePowerFactorString;

	@Ignore
	private PowerFactor powerFactor;

	public Competitor() { }

	public Competitor(String firstName, String lastName, String practiScoreId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.practiScoreId = practiScoreId;
	}

	@Override
	public int compareTo(@NonNull Competitor other) {
		int result = this.lastName.compareTo(other.getLastName());
		if (result != 0) return result;
		return this.firstName.compareTo(other.getFirstName());
	}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.trim();
	}

	public boolean isDisqualified() {
		return disqualified;
	}

	public void setDisqualified(boolean disqualified) {
		this.disqualified = disqualified;
	}

	public String getPractiScorePowerFactorString() {
		return practiScorePowerFactorString;
	}

	public void setPractiScorePowerFactorString(String practiScorePowerFactorString) {
		this.practiScorePowerFactorString = practiScorePowerFactorString;
		this.powerFactor = PowerFactor.valueOf(practiScorePowerFactorString.toUpperCase());
	}

	public PowerFactor getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(PowerFactor powerFactor) {
		this.powerFactor = powerFactor;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public void setPractiScoreDivisionString(String practiScoreDivisionString) {
		setDivision(Division.fromString(practiScoreDivisionString));
	}
}