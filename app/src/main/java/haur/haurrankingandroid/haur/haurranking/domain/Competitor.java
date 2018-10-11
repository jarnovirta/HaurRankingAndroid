package haur.haurrankingandroid.haur.haurranking.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Competitor {

	private Long id;

	private Match match;

	private Long matchId;

	@JsonProperty("sh_uid")
	private String practiScoreId;

	@JsonProperty("sh_num")
	private int shooterNumber;

	@JsonProperty("sh_fn")
	private String firstName;

	@JsonProperty("sh_ln")
	private String lastName;

	@JsonProperty("sh_id")
	private String ipscAlias;

	@JsonProperty("sh_dvp")
	private String division;

	private List<String> categories;

	@JsonProperty("sh_ctgs")
	private String practiScoreCategoryString;

	@JsonProperty("sh_dq")
	private boolean disqualified;

	@JsonProperty("sh_team")
	private String team;

	@JsonProperty("sh_del")
	private boolean deleted = false;

	@JsonProperty("sh_pf")
	private String practiScorePowerFactorString;

	@JsonProperty("sh_cc")
	private String country;

	private PowerFactor powerFactor;

	@JsonProperty("sh_sqd")
	private int squad;

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

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public int getShooterNumber() {
		return shooterNumber;
	}

	public void setShooterNumber(int shooterNumber) {
		this.shooterNumber = shooterNumber;
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

	public String getPractiScoreDivisionString() {
		return division;
	}

	public boolean isDisqualified() {
		return disqualified;
	}

	public void setDisqualified(boolean disqualified) {
		this.disqualified = disqualified;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPractiScorePowerFactorString() {
		return practiScorePowerFactorString;
	}

	public void setPractiScorePowerFactorString(String practiScorePowerFactorString) {
		this.practiScorePowerFactorString = practiScorePowerFactorString;
		this.powerFactor = PowerFactor.valueOf(practiScorePowerFactorString.toUpperCase());
	}

	public int getSquad() {
		return squad;
	}

	public void setSquad(int squad) {
		this.squad = squad;
	}

	public String getIpscAlias() {
		return ipscAlias;
	}

	public void setIpscAlias(String ipscAlias) {
		this.ipscAlias = ipscAlias;
	}

	public PowerFactor getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(PowerFactor powerFactor) {
		this.powerFactor = powerFactor;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void setPractiScoreDivisionString(String practiScoreDivisionString) {
		this.division = practiScoreDivisionString;
	}

	public void setPractiScoreCategoryString(String categoriesString) {
		this.practiScoreCategoryString = categoriesString;
		if (this.categories == null) this.categories = new ArrayList<String>();
		practiScoreCategoryString = practiScoreCategoryString.replace("\"", "");
		practiScoreCategoryString = practiScoreCategoryString.replace("[", "");
		practiScoreCategoryString = practiScoreCategoryString.replace("]", "");
		practiScoreCategoryString = practiScoreCategoryString.replace(" ", "");
		String[] categoryStrings = practiScoreCategoryString.split(",");
		for (String category : categoryStrings) {
			this.categories.add(category);
		}
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getPractiScoreCategoryString() {
		return practiScoreCategoryString;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}