/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.timeline;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Minutes;

public class DefaultTimeFormatter {

	public String format(DateTime dateFrom, DateTime dateTo) {
		Minutes minutes = Minutes.minutesBetween(dateFrom, dateTo);
		int minutesInt = minutes.getMinutes();
		int moduloMins = minutesInt % DateTimeConstants.MINUTES_PER_HOUR;
		int hoursInt = minutes.toStandardHours().getHours();
		
		String output = "";

		if (hoursInt > 1) {
			output += hoursInt + " hours ";
		} else if (hoursInt != 0) {
			output += hoursInt + " hour ";
		}

		if (moduloMins == 1) {
			output += moduloMins + " min ";
		} else if (moduloMins > 1) {
			output += moduloMins + " mins ";
		}

		return output + "ago";
	}
}
