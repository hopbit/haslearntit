package it.haslearnt.entry.difficulty;

import it.haslearnt.cassandra.mapping.CassandraRepository;
import it.haslearnt.entry.Entry;

import java.util.List;

import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.CounterColumn;
import org.scale7.cassandra.pelops.Bytes;
import org.scale7.cassandra.pelops.Selector;
import org.springframework.stereotype.Repository;

@Repository
public class DifficultyRepository extends CassandraRepository<Entry>{
	public String getDifficultyFor(String skill) {
		List<CounterColumn> columns = pool.createSelector()
				.getCounterColumnsFromRow("DifficultyLevel", Bytes.fromUTF8("all"), 
						Selector.newColumnsPredicate(skill, skill+"\255", false, 3), ConsistencyLevel.ONE);
		String maxDiff="medium";
		long maxVal=0;
		for (CounterColumn counterColumn : columns) {
			if (maxVal<counterColumn.value){
				maxVal=counterColumn.value;
				String temp=Bytes.toUTF8(counterColumn.name);
				maxDiff=temp.split("\0")[1];
			}
		}
		
		return maxDiff;
	}
}
