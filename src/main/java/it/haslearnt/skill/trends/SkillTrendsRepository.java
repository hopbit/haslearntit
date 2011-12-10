package it.haslearnt.skill.trends;

import it.haslearnt.cassandra.mapping.*;
import it.haslearnt.entry.*;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.google.common.collect.*;

@Repository
public class SkillTrendsRepository extends CassandraRepository<SkillTrend> {

	@Autowired
	EntryRepository entryRepository;

	public List<SkillTrend> loadTop(int number) {
		List<SkillTrend> entries = Lists.newArrayList();

		for (String skill : entryRepository.fetchSkills())
			entries.add(load(skill));

		Collections.sort(entries);

		List<SkillTrend> result = new ArrayList<SkillTrend>();
		for (int i = 0; i < number; i++)
			result.add(entries.get(i));

		return result;
	}
}
