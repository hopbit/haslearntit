package it.haslearnt.entry;

import it.haslearnt.cassandra.mapping.*;

import org.apache.commons.lang.builder.*;

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

	private boolean completed;

	private int learningTime;

	private TimeType timeType;

	private int points;

	public String what() {
		return skill;
	}

	public String when() {
		return when;
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

	public void build() {
		calculatePoints();
	}

	private void calculatePoints() {
		this.points = (int) (learningTime * difficultyFactor());
	}

	private double difficultyFactor() {
		if ("easy".equals(difficulty))
			return 1;
		if ("hard".equals(difficulty))
			return 1.4;

		return 0;
	}

	public int points() {
		return points;
	}

	public Entry itTook(int learningTime, TimeType timeType) {
		this.learningTime = learningTime;
		this.timeType = timeType;
		return this;
	}

	public void andIveCompleted() {
		this.completed = true;
	}

	public boolean isCompleted() {
		return this.completed;
	}
}
