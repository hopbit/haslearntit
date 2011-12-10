package it.haslearnt.statistics;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import setup.IntegrationTest;

@RunWith(MockitoJUnitRunner.class)
public class UserStaticticsRepositoryTest extends IntegrationTest {

    @Autowired
    UserStaticticsRepository repository;
    private String user = "tomrek";
    private String time = "11";

    @Test
    @Ignore
    public void shouldSaveNewUserStatistics() {
        // UserStatistics userStatistics = new UserStatistics().withUserName(user).withTotalLearningTime(time);

        repository.addNewTimeForUser(user, time);

        UserStatistics fetchedUserStatistics = repository.loadStatisticsForUser(user);

        // assertNotNull(fetchedEntry);
        // assertEquals("something", fetchedEntry.what());
        // assertEquals("today", fetchedEntry.when());
        // assertEquals("easy", fetchedEntry.howDifficult());
        // assertEquals(10, fetchedEntry.points());
    }

}
