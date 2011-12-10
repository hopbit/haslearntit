package it.haslearnt.timeline;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class PreetyTimeFormaterTest {

    @Test
    @Parameters({
            "1, 1 minute",
            "25, 25 minutes",
            "61, 1 hour 1 minutes"})
    public void testConvert(int minutes, String expectedText) {
        // given
        PreetyTimeFormater preetyTimeFormater = new PreetyTimeFormater();

        // when
        String text = preetyTimeFormater.formatMinutes(minutes);

        // then
        assertEquals(expectedText, text);
    }
}
