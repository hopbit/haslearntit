package it.haslearnt.statistics;

import it.haslearnt.cassandra.mapping.CassandraRepository;

import org.springframework.stereotype.Component;

@Component
public class UserStaticticsRepository extends CassandraRepository<UserStatistics> {

    public int getHoursForUser(String userName) {
        return 0;
    }

    public void addLearningTimeForUser(String userName, int learningTime) {

    }

    public void addNewTimeForUser(String user, String time) {
        // TODO Auto-generated method stub

    }

    public UserStatistics loadStatisticsForUser(String user) {
        // TODO Auto-generated method stub
        return null;
    }
}
