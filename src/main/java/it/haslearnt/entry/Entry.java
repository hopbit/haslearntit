package it.haslearnt.entry;

import it.haslearnt.cassandra.mapping.Column;
import it.haslearnt.cassandra.mapping.Entity;
import it.haslearnt.cassandra.mapping.EntityWithGeneratedId;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.Duration;

@Entity("Entries")
public class Entry extends EntityWithGeneratedId {

	public enum DifficultyLevel {
		EASY,
		MEDIUM,
		HARD
	}

	@Column("skill")
	private String skill;

	@Column("when")
	private String when;

	@Column("difficulty")
	private String difficulty;

	@Column("skill_completed")
	private String completed = "false";

	private int learningTime;

	@Column("points")
	private String points = "0";

	public String what() {
		return skill;
	}

	public String when() {
		return when;
	}

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String howDifficult() {
		return difficulty;

	}

	public Entry iveLearnt(String skill) {
		this.skill = skill;
		return this;
	}

	public Entry today() {
		return when("today");
	}

	public Entry andItWas(String difficulty) {
		this.difficulty = difficulty;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
	}

	public Entry when(String when) {
		this.when = when;
		return this;
	}

	public Entry build() {
		return this;
	}

	public int points() {
		return (int) Double.valueOf(points).doubleValue();
	}

	public Entry itTookInMinutes(int learningTime) {
		this.learningTime = learningTime;
		return this;
	}

	public Entry andIveCompleted() {
		completed = "true";
		return this;
	}

	public boolean isCompleted() {
		return Boolean.valueOf(completed);
	}

	public Duration getLearingDuration() {
		return Duration.standardMinutes(learningTime);
	}
	
	public String getDifficulty() {
		return difficulty;
	}

	public int getLearningTime() {
		return learningTime;
	}

	public Entry gainedPoints(int points) {
		this.points = String.valueOf(points);
		return this;
	}
}
