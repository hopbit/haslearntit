package it.haslearnt.timeline;

public class PreetyTimeFormater {


    public static final int MINUTES_PER_HOUR = 60;
    private static final int ONE_MINUTE = 1;

    public String formatMinutes(int minutes) {
        return generateHours(minutes) + generateMinutes(minutes);
    }

    private String generateHours(int minutes) {
        if (minutes < MINUTES_PER_HOUR) {
            return "";
        }
        return minutes / MINUTES_PER_HOUR + " hour ";
    }

    private String generateMinutes(int minutes) {
        String postfix = " minute" ;
        if (minutes > ONE_MINUTE) {
            postfix += "s";
        }
        return minutes % MINUTES_PER_HOUR + postfix;
    }
}
