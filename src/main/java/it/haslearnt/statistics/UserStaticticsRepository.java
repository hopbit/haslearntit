package it.haslearnt.statistics;

import static org.apache.cassandra.thrift.ConsistencyLevel.*;
import it.haslearnt.cassandra.mapping.CassandraRepository;

import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.scale7.cassandra.pelops.Bytes;
import org.scale7.cassandra.pelops.Mutator;
import org.springframework.stereotype.Component;

@Component
public class UserStaticticsRepository extends CassandraRepository<UserStatistics> {

    public void addNewTimeForUser(final String user, final int time) {
        final long newTime = findTimeForUser(user) + time;
        Mutator mutator = pool.createMutator();

        Column timeColumn = mutator.newColumn("TotalLearningTime", String.valueOf(newTime));
        mutator.writeColumn("UserStatistics", user, timeColumn);

        mutator.execute(ONE);
    }

    public UserStatistics loadStatisticsForUser(final String user) {
        long totalTime = findTimeForUser(user);
        UserStatistics result = new UserStatistics().withUserName(user).withTotalLearningTime(totalTime);
        return result;
    }

    private long findTimeForUser(final String user) {
        List<Column> columns = pool.createSelector().getColumnsFromRow("UserStatistics", user, false, ONE);
        long totalTime = 0;
        for (Column column : columns) {
            if ("TotalLearningTime".equals(Bytes.toUTF8(column.getName()))) {
                totalTime = Long.valueOf(Bytes.toUTF8(column.getValue()));
                break;
            }
        }
        return totalTime;
    }
}
