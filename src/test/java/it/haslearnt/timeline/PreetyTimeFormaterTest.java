package it.haslearnt.timeline;

import junitparams.Parameters;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PreetyTimeFormaterTest {

//    @Test
//    public void shouldConvertOneMinute() {
//        testConvert(1, "1 minute");
//    }

    @Test
    @Parameters(method="")
    public void shouldConvertMinutes() {
        testConvert(25, "25 minutes");
    }

    @Test
    public void shouldConvertMoreThen60minutes() throws Exception {
        testConvert(61, "1 hour 1 minutes");
    }

     @Parameters({
             "25, 25 minutes",
             "61, 1 hour 1 minutes" })
    private void testConvert(int minutes, String expectedText) {
        // given
        PreetyTimeFormater preetyTimeFormater = new PreetyTimeFormater();

        // when
        String text = preetyTimeFormater.formatMinutes(minutes);

        // then
        assertEquals(expectedText, text);
    }
}
