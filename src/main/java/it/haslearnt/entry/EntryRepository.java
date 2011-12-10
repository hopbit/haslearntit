package it.haslearnt.entry;

import static org.apache.cassandra.thrift.ConsistencyLevel.*;
import static org.springframework.util.Assert.*;
import it.haslearnt.cassandra.mapping.*;
import it.haslearnt.skill.trends.*;

import java.util.*;

import org.apache.cassandra.thrift.Column;
import org.scale7.cassandra.pelops.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.google.common.collect.*;

@Repository
public class EntryRepository extends CassandraRepository<Entry> {

	@Autowired
	SkillTrendsRepository skillTrendsRepository;

	public List<Entry> fetchForUser(String user) {
		List<Column> columns = pool.createSelector().getColumnsFromRow("UserEntries", user, true, ONE);
		List<Entry> entries = Lists.newArrayList();
		for (Column column : columns) {
			entries.add(load(Bytes.toUTF8(column.getValue())));
		}
		return entries;
	}

	public List<Entry> fetchForUser(String user, int limit, int offset) {
		isTrue(limit > 0);
		isTrue(offset >= 0);
		hasText(user);
		return fetchForUser(user);
	}

	public void saveEntry(Entry entry, String user) {
		save(entry);
		Mutator mutator = pool.createMutator();

		saveUserEntry(entry, user, mutator);
		updateSkills(entry, mutator);
		updateSkillTrends(entry);

		mutator.execute(ONE);
	}

	private void saveUserEntry(Entry entry, String user, Mutator mutator) {
		Column column = mutator.newColumn(getNewEntryColumnName(user), entry.id());
		mutator.writeColumn("UserEntries", user, column);
	}

	private void updateSkills(Entry entry, Mutator mutator) {
		Column skillColumn = mutator.newColumn(entry.what());
		mutator.writeColumn("SkillEntries", "SkillRowKey", skillColumn);
	}

	private void updateSkillTrends(Entry entry) {
		SkillTrend skillTrend = skillTrendsRepository.load(entry.what());

		if (skillTrend != null)
			skillTrend.learntBy(skillTrend.learntBy() + 1);
		else
			skillTrend = new SkillTrend().withSkill(entry.what()).learntBy(1);

		skillTrendsRepository.save(skillTrend);
	}

	private String getNewEntryColumnName(String user) {
		int noOfEntries = fetchForUser(user).size();
		return "entry" + noOfEntries;
	}

	public List<String> fetchSkills() {
		List<Column> columns = pool.createSelector().getColumnsFromRow("SkillEntries", "SkillRowKey", false, ONE);
		List<String> skills = Lists.newArrayList();
		for (Column column : columns) {
			skills.add(Bytes.toUTF8(column.getName()));
		}
		return skills;
	}

}
