package it.haslearnt.statistics;

import it.haslearnt.cassandra.mapping.Entity;

@Entity("UserStatistics")
public class UserStatistics {
    // @Id
    private String userName;

    // @Column("total_learning_time")
    private long totalLearningTime;

    public UserStatistics withUserName(String name) {
        userName = name;
        return this;
    }

    public UserStatistics withTotalLearningTime(long time) {
        totalLearningTime = time;
        return this;
    }

    public long getTotalTimeSpent() {
        return totalLearningTime;
    }

    public String getUserName() {
        return userName;
    }

    public long getTotalLearningTime() {
        return totalLearningTime;
    }

}
