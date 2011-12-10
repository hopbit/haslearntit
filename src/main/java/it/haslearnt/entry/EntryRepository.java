package it.haslearnt.entry;

import com.google.common.collect.Lists;
import it.haslearnt.cassandra.mapping.CassandraRepository;
import org.apache.cassandra.thrift.Column;
import org.scale7.cassandra.pelops.Bytes;
import org.scale7.cassandra.pelops.Mutator;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.apache.cassandra.thrift.ConsistencyLevel.ONE;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isTrue;

@Repository
public class EntryRepository extends CassandraRepository<Entry> {

	public List<Entry> fetchForUser(String user) {
		List<Column> columns = pool.createSelector().getColumnsFromRow("UserEntries", user, true, ONE);
		List<Entry> entries = Lists.newArrayList();
		for (Column column : columns) {
			entries.add(load(Bytes.toUTF8(column.getValue())));
		}
		return entries;
	}

    public List<Entry> fetchForUser(String user, int limit, int offset) {
        isTrue(limit > 0); isTrue(offset >= 0); hasText(user);
        return fetchForUser(user);
	}

	public void saveEntry(Entry entry, String user) {
		save(entry);
		Mutator mutator = pool.createMutator();
		
		Column column = mutator.newColumn(getNewEntryColumnName(user), entry.id());
		mutator.writeColumn("UserEntries", user, column);
		
		Column skillColumn = mutator.newColumn(entry.what());
		mutator.writeColumn("SkillEntries", "SkillRowKey", skillColumn);
		
		mutator.execute(ONE);
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
