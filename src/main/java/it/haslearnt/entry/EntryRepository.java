package it.haslearnt.entry;

import static org.apache.cassandra.thrift.ConsistencyLevel.*;
import it.haslearnt.cassandra.mapping.CassandraRepository;

import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.scale7.cassandra.pelops.Mutator;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository
public class EntryRepository extends CassandraRepository<Entry> {

    public List<Entry> fetchForUser(String user) {
        List<Column> columns = pool.createSelector().getColumnsFromRow("UserEntries", user, true, ONE);
        List<Entry> entries = Lists.newArrayList();
        for (Column column : columns) {
            entries.add(load(new String(column.getValue())));
        }
        return entries;
    }

    public void saveEntry(Entry entry, String user) {
        save(entry);
        Mutator mutator = pool.createMutator();
        Column column = mutator.newColumn(getNewEntryColumnName(user), entry.id());
        mutator.writeColumn("UserEntries", user, column);
        mutator.execute(ONE);
    }

    private String getNewEntryColumnName(String user) {
        int noOfEntries = fetchForUser(user).size();
        String columnName = "entry" + noOfEntries;
        return columnName;
    }

}
