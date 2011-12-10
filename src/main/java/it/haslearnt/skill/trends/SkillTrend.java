package it.haslearnt.skill.trends;

import it.haslearnt.cassandra.mapping.*;

@Entity("SkillTrends")
public class SkillTrend implements Comparable<SkillTrend> {

	@Id
	private String skill;

	@Column("numberOfPeople")
	private String numberOfPeople;

	public SkillTrend withSkill(String skill) {
		this.skill = skill;
		return this;
	}

	public SkillTrend learntBy(int numberOfPeople) {
		this.numberOfPeople = Integer.toString(numberOfPeople);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numberOfPeople == null) ? 0 : numberOfPeople.hashCode());
		result = prime * result + ((skill == null) ? 0 : skill.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkillTrend other = (SkillTrend) obj;
		if (numberOfPeople == null) {
			if (other.numberOfPeople != null)
				return false;
		} else if (!numberOfPeople.equals(other.numberOfPeople))
			return false;
		if (skill == null) {
			if (other.skill != null)
				return false;
		} else if (!skill.equals(other.skill))
			return false;
		return true;
	}

	@Override
	public int compareTo(SkillTrend o) {
		return Integer.valueOf(o.numberOfPeople) - Integer.valueOf(numberOfPeople);
	}

	public String skill() {
		return skill;
	}

	public int learnedBy() {
		return Integer.valueOf(numberOfPeople);
	}
}
