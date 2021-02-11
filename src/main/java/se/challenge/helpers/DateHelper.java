package se.challenge.helpers;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    // Given a date object, returns that date with the day of month set to 01 if the current day is 15 or below, otherwise 16
    // This is used as a key for a TreeMap while preparing data for the report for convenience
    public static Date GetDateKey(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int newDay = day <= 15 ? 1 : 16;
        cal.set(Calendar.DAY_OF_MONTH, newDay);
        return new Date(cal.getTimeInMillis());
    }

    // Given a date object, returns that date with the day of month set to 15 if the current day is 15 or below, otherwise it is set to the last day of the month
    public static String GetEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day <= 15) {
            cal.set(Calendar.DAY_OF_MONTH, 15);
            return GetDateAsString(new Date(cal.getTimeInMillis()));
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return GetDateAsString(new Date(cal.getTimeInMillis()));
    }

    // Given a date object, returns a string representing the date in "yyyy-mm-dd" format as per specification
    public static String GetDateAsString(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return dateFormatter.format(calendar.getTime());
    }

}
