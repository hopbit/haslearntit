package it.haslearnt.controller;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import predefinedData.UserPredefinedData;
import setup.IntegrationTest;

public class UserTimelineControllerIntegrationTest extends IntegrationTest {

    @Autowired
    private UserTimelineController controller;

    @Test
    public void shouldReturnUserTimeline() {
        // given
        authenticateTestUser();
        Model model = new ExtendedModelMap();

        //when
        String viewName = controller.showUserTimeline(UserPredefinedData.name, model);

        //then
        Assert.assertEquals(UserTimelineController.userTimelineView, viewName);
        Assert.assertTrue(model.containsAttribute(UserTimelineController.userTimeLineKey));
    }
}
