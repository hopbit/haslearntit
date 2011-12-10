package it.haslearnt.statistics;

import it.haslearnt.cassandra.mapping.Column;
import it.haslearnt.cassandra.mapping.Entity;
import it.haslearnt.cassandra.mapping.Id;

@Entity("UserStatistics")
public class UserStatistics {
    @Id
    private String userName;

    @Column("total_learning_time")
    private int totalLearningTime;

    public UserStatistics withUserName(String name) {
        userName = name;
        return this;
    }

    public UserStatistics withTotalLearningTime(int time) {
        totalLearningTime = time;
        return this;
    }

	public int getTotalTimeSpent() {
		return totalLearningTime;
	}

}
