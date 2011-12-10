/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.timeline;

import org.joda.time.DateTime;
import static org.joda.time.DateTimeConstants.*;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class DefaultTimeFormatterTest {

	private DefaultTimeFormatter sut;
	
	@Before
	public void setUp() {
		sut = new DefaultTimeFormatter();
	}

	@Test
	public void shouldReturnMinuteForOneMinute() {
		expectResultForDifferenceInSeconds(SECONDS_PER_MINUTE, "1 min");

	}

	@Test
	public void shouldReturnMinutesIfUnderOneHour() {
		expectResultForDifferenceInSeconds(SECONDS_PER_MINUTE * 2, "2 mins");

	}

	@Test
	public void shouldReturnJustHourIfExactlyOneHour() {
		expectResultForDifferenceInSeconds(SECONDS_PER_HOUR, "1 hour");
	}

	@Test
	public void shouldReturnHourAndMinsIfOverOneHour() {
		expectResultForDifferenceInSeconds(SECONDS_PER_HOUR + SECONDS_PER_MINUTE * 15, "1 hour 15 mins");
	}

	@Test
	public void shouldReturnHoursAndMinsIfOverTwoHours() {
		expectResultForDifferenceInSeconds(SECONDS_PER_HOUR * 2 + SECONDS_PER_MINUTE * 17, "2 hours 17 mins");
	}

	@Test
	public void shouldReturnHoursIfOverOneDay() {
		expectResultForDifferenceInSeconds(SECONDS_PER_HOUR * 25 + SECONDS_PER_MINUTE * 17, "25 hours 17 mins");
	}

	private void expectResultForDifferenceInSeconds(int seconds, String expected) {
		//given
		DateTime dateFrom = new DateTime();
		DateTime dateTo = dateFrom.plusSeconds(seconds);

		//when
		String result = sut.format(dateFrom, dateTo);

		//then
		assertThat(result).isEqualTo(expected);
	}


	
	
}
