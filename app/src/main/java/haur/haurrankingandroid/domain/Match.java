package haur.haurrankingandroid.domain;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import haur.haurrankingandroid.data.dao.TypeConverters.DateConverter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {

	@PrimaryKey(autoGenerate = true)
	private Long id;

	@JsonProperty("match_id")
	private String practiScoreId;

	@JsonProperty("match_name")
	private String name;

	@JsonProperty("match_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@TypeConverters({DateConverter.class})
	private Date date;

	@JsonProperty("match_stages")
	@Ignore
	private List<Stage> stages;

	@JsonProperty("match_shooters")
	@Ignore
	private List<Competitor> competitors;

	@Ignore
	private List<ScoreCard> scoreCards;

	public Match() { }

	public Match(Long id, String practiScoreId, String name,
	             String level, Date date) {
		this.id = id;
		this.practiScoreId = practiScoreId;
		this.name = name;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

	public List<Competitor> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<Competitor> competitors) {
		this.competitors = competitors;
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

	public List<ScoreCard> getScoreCards() {
		return scoreCards;
	}

	public void setScoreCards(List<ScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}
}