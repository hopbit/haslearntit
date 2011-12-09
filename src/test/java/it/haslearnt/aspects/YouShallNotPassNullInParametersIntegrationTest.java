/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.aspects;

import org.junit.Test;
import setup.IntegrationTest;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

public class YouShallNotPassNullInParametersIntegrationTest extends IntegrationTest {
    @Resource(name = "plainOldJavaObject")
    private PlainOldJavaObject plainOldJavaObject;

    @Resource(name = "simpleDto")
    private SimpleDto simpleDto;

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfAnyParameterIsNull() {
        // when
        plainOldJavaObject.wouldYouDareToPassMeANull(null);

        // then exception is thrown
    }

    @Test
    public void shouldProvideVerboseExceptionMessage() {
        //given
        Integer nullArgumentIndex = 0;

        try {
            //when
            plainOldJavaObject.wouldYouDareToPassMeANull(null);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //then
            assertThat(e.getMessage()).contains("at index " + nullArgumentIndex);
        }
    }

    @Test
    public void shouldNotThrowExceptionOnPassingNullToSetter() {
        // when
        plainOldJavaObject.setString(null);

        // then no exception is thrown
    }

    @Test
    public void shouldBeSafeToPassNullToDtos() {
        // when
        simpleDto.youCanSafelyPassNullToDtos(null);

        // then no exception is thrown
    }

}
