/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.cassandra;

import static it.haslearnt.cassandra.CassandraColumnFamilyManager.addColumnFamilyIfNeeded;
import static it.haslearnt.cassandra.CassandraColumnFamilyManager.recreateColumnFamily;
import static it.haslearnt.cassandra.ColumnFamilyType.CounterColumnType;
import static it.haslearnt.cassandra.ColumnFamilyType.Simple;

import java.util.HashMap;
import java.util.Map;

import org.scale7.cassandra.pelops.spring.CommonsBackedPoolFactoryBean;

public class CassandraConnectionProvider extends CommonsBackedPoolFactoryBean {

	private boolean clean = false;

	@SuppressWarnings("serial")
	Map<String, String> columnFamilyToComparator = new HashMap<String, String>() {
		{
			put("Notes", "LongType");
			put("Entries", "UTF8Type");
			put("UserEntries", "UTF8Type");
			put("SkillEntries", "UTF8Type");
			put("SkillTrends", "UTF8Type");
			put("Users", "UTF8Type");
			put("UserOpenId", "UTF8Type");
			put("UserStatistics", "UTF8Type");
		}
	};

	@SuppressWarnings("serial")
	Map<String, String> counterColumnFamillyToComparator = new HashMap<String, String>() {
		{
			put("DifficultyLevel", "UTF8Type");
		}
	};

	@Override
	public void afterPropertiesSet() throws Exception {
		CassandraKeyspaceManager.ensureKeyspace(getCluster(), getKeyspace());
		for (String columnFamily : columnFamilyToComparator.keySet()) {
			String comparator = columnFamilyToComparator.get(columnFamily);
			if (clean) {
				recreateColumnFamily(getCluster(), getKeyspace(), columnFamily, comparator, Simple);
			} else {
				addColumnFamilyIfNeeded(getCluster(), getKeyspace(), columnFamily, comparator, Simple);
			}
		}
		for (String columnFamily : counterColumnFamillyToComparator.keySet()) {
			String comparator = counterColumnFamillyToComparator.get(columnFamily);
			if (clean) {
				recreateColumnFamily(getCluster(), getKeyspace(), columnFamily, comparator, CounterColumnType);
			} else {
				addColumnFamilyIfNeeded(getCluster(), getKeyspace(), columnFamily, comparator, CounterColumnType);
			}
		}
		super.afterPropertiesSet();
	}

	public void setClean(boolean clean) {
		this.clean = clean;
	}
}
