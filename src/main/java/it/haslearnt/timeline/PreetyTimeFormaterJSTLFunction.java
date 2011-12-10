package it.haslearnt.timeline;

import org.joda.time.Duration;

public class PreetyTimeFormaterJSTLFunction {

    private static PreetyTimeFormater formater = new PreetyTimeFormater();
    private static DefaultTimeFormatter defaultFormater = new DefaultTimeFormatter();
    

    public static String format(int timeInMinutes) {

        return formater.formatMinutes(timeInMinutes);

    }
    
    
    public static String format(Duration duration) {
        return defaultFormater.format(duration);
    }
}
