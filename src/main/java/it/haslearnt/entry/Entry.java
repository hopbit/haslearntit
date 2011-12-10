package it.haslearnt.entry;

import it.haslearnt.cassandra.mapping.Column;
import it.haslearnt.cassandra.mapping.Entity;
import it.haslearnt.cassandra.mapping.EntityWithGeneratedId;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity("Entries")
public class Entry extends EntityWithGeneratedId {

	public enum TimeType {
		MINUTES
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

	private TimeType timeType;

	@Column("points")
	private String points;

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
		calculatePoints();
		return this;
	}

	private void calculatePoints() {
		points = Double.toString(learningTime * difficultyFactor());
	}

	public int getLearningTime() {
		return learningTime;
	}

	private double difficultyFactor() {
		if ("easy".equals(difficulty)) {
			return 1;
		}
		if ("medium".equals(difficulty)) {
			return 1.2;
		}
		if ("hard".equals(difficulty)) {
			return 1.4;
		}

		return 0;
	}

	public int points() {
		return (int) Double.valueOf(points).doubleValue();
	}

	public Entry itTook(int learningTime, TimeType timeType) {
		this.learningTime = learningTime;
		this.timeType = timeType;
		return this;
	}

	public Entry andIveCompleted() {
		completed = "true";
		return this;
	}

	public boolean isCompleted() {
		return Boolean.valueOf(completed);
	}
}
