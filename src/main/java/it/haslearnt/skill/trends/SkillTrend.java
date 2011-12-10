package it.haslearnt.skill.trends;

public class SkillTrend {

	private String skill;
	private int numberOfPeople;

	public SkillTrend withSkill(String skill) {
		this.skill = skill;
		return this;
	}

	public SkillTrend learntBy(int numberOfPeople) {

		this.numberOfPeople = numberOfPeople;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numberOfPeople;
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
		if (numberOfPeople != other.numberOfPeople)
			return false;
		if (skill == null) {
			if (other.skill != null)
				return false;
		} else if (!skill.equals(other.skill))
			return false;
		return true;
	}

}
