package it.haslearnt.statistics;

import it.haslearnt.statistics.StaticticsController;
import it.haslearnt.statistics.UserStaticticsRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

public class StaticticsControllerTest {

    private UserStaticticsRepository service = Mockito.mock(UserStaticticsRepository.class);
    private StaticticsController controler = new StaticticsController();

    @Before
    public void setupServiceInController() {
        controler.userStaticticsService = service;
    }

    @Test
    public void shouldReturnViewName() throws Exception {
        // given
        String userName = null;

        // when
        ModelAndView totalLearningTime = controler.getTotalLearningTime(userName);

        // then
        Assert.assertEquals("user", totalLearningTime.getViewName());
    }

    @Test
    public void shouldParametrExists() throws Exception {
        // given
        String userName = null;

        // when
        ModelAndView totalLearningTime = controler.getTotalLearningTime(userName);

        // then
        Assert.assertTrue(totalLearningTime.getModel().containsKey("hours"));
    }

    @Test
    public void shouldGetHoursFromService() {
        // given
        final String userName = "mariusz";
        Mockito.when(service.getHoursForUser(userName)).thenReturn(5);

        // when
        ModelAndView totalLearningTime = controler.getTotalLearningTime(userName);

        // then
        Assert.assertEquals(5, totalLearningTime.getModel().get("hours"));
        Mockito.verify(service).getHoursForUser(userName);
    }

}
