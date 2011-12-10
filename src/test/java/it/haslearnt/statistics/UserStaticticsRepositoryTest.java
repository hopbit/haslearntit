package it.haslearnt.statistics;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import setup.IntegrationTest;

public class UserStaticticsRepositoryTest extends IntegrationTest {

    @Autowired
    UserStaticticsRepository repository;

    private String user = "tomek";
    private int time = 11;

    @Test
    public void shouldAddNewTimeSliceForUser() {
        // given
        // /when
        repository.addNewTimeForUser(user, time);
        // then
        UserStatistics fetchedUserStatistics = repository.loadStatisticsForUser(user);
        assertNotNull(fetchedUserStatistics);
        assertEquals(user, fetchedUserStatistics.getUserName());
        assertEquals(time, fetchedUserStatistics.getTotalLearningTime());
    }

    @Test
    public void shouldAddTwoTimeSlicesForUser() {
        // given
        // /when
        repository.addNewTimeForUser(user, time);
        repository.addNewTimeForUser(user, 2 * time);
        // then
        UserStatistics fetchedUserStatistics = repository.loadStatisticsForUser(user);
        assertNotNull(fetchedUserStatistics);
        assertEquals(user, fetchedUserStatistics.getUserName());
        assertEquals(3 * time, fetchedUserStatistics.getTotalLearningTime());
    }

    @Test
    public void shouldReturnZeroForUnknownUser() {
        // given
        final String unknownUser = "does_not_exist";

        // /when
        repository.addNewTimeForUser(user, time);
        repository.addNewTimeForUser(user, 2 * time);
        // then
        UserStatistics fetchedUserStatistics = repository.loadStatisticsForUser(unknownUser);
        assertNotNull(fetchedUserStatistics);
        assertEquals(unknownUser, fetchedUserStatistics.getUserName());
        assertEquals(0, fetchedUserStatistics.getTotalLearningTime());
    }
}
