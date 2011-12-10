package it.haslearnt.timeline;

public class PreetyTimeFormaterJSTLFunction {

    private static PreetyTimeFormater formater = new PreetyTimeFormater();

    public static String format(int timeInMinutes) {

        return formater.formatMinutes(timeInMinutes);

    }
}
