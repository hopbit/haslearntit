package it.haslearnt.skill.trends;

import it.haslearnt.cassandra.mapping.*;

import java.util.*;

import org.springframework.stereotype.*;

@Repository
public class SkillTrendsRepository extends CassandraRepository<SkillTrend> {

	public List<SkillTrend> loadTop(int i) {
		return null;
	}

}
