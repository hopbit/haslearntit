package it.haslearnt.statistics;

import junit.framework.Assert;

import org.junit.Test;

public class UserStatisticsTest {
	
	@Test
	public void shouldGetTotalTimeSpent() {
		UserStatistics userStatistics = new UserStatistics().withTotalLearningTime(123);
		Assert.assertTrue(userStatistics.getTotalTimeSpent() == 123);
	}

}
