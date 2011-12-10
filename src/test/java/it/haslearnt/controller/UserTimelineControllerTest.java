package it.haslearnt.controller;

import it.haslearnt.commonExceptions.ResourceNotFoundException;
import it.haslearnt.entry.EntryRepository;
import it.haslearnt.user.User;
import it.haslearnt.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.scale7.cassandra.pelops.exceptions.ModelException;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserTimelineControllerTest {

    @Mock
    Model model;

    @Mock
    EntryRepository entryRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    List userTimeLineEntryList;

    String userName ="bob";

    UserTimelineController userTimerController;

    @Mock
    User user;

    @Before
    public void setUp() {
        userTimerController = new UserTimelineController();
        userTimerController.entryRepository = entryRepository;
        userTimerController.userRepository = userRepository;
    }


    @Test
    public void shouldShowUserTimeLineAndReturnView() {
        //given
        given(entryRepository.fetchForUser(userName)).willReturn(userTimeLineEntryList);
        given(userRepository.load(userName)).willReturn(user);

        //when
        String toView = userTimerController.showUserTimeline(userName, model);

        //then
        verify(model).addAttribute(UserTimelineController.userTimeLineKey,userTimeLineEntryList);
        assertEquals(UserTimelineController.userTimeLineKey, toView);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldReturn404IfGivenUserNotExist() {
        //given
        given(userRepository.load(userName)).willReturn(null);

        // when
        String toView = userTimerController.showUserTimeline(userName, model);

        //then exception is thrown
    }

     @Test(expected = ResourceNotFoundException.class)
    public void shouldReturn404OnDBException() {
        //given
        given(userRepository.load(userName)).willThrow(ModelException.class);

        // when
        String toView = userTimerController.showUserTimeline(userName, model);

        //then exception is thrown
    }
}
