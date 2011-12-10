/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.timeline;


public class DefaultTimeFormatter {
	
	public static String DAY = "day";
	public static String HOUR = "hour";
	public static String MIN = "min";


	public String format(org.joda.time.Duration duration) {
		long dd = duration.getStandardDays();
		long hh = duration.getStandardHours() - dd*24;
		long min = duration.getStandardMinutes() - duration.getStandardHours()*60;
		
		StringBuffer sb = new StringBuffer();
		if (dd>0) {
			sb.append(dd).append(" ").append(getCardinalName(dd,DAY)).append(" ");
		}
		if (hh>0) {
			sb.append(hh).append(" ").append(getCardinalName(hh,HOUR)).append(" ");
		}
		if (min>0) {
			sb.append(min).append(" ").append(getCardinalName(min,MIN));
		}
		
		return sb.toString().trim();
	}
	
	
	private String getCardinalName(long amount, String uom){
		return amount>1?uom+"s":uom;
	}
}
