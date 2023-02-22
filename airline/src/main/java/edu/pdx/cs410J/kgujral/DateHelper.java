package edu.pdx.cs410J.kgujral;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateHelper {
    private static final String dateFormat = "%s/%s/%s %s:%s %s";
   /** Convert String to Date*/
    public static Date stringToDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        return stringToDate(date, formatter);
    }
    /** Convert String to Date*/
    public static Date shortStringToDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy, hh:mm a");
        return stringToDate(date, formatter);
    }
    /** Convert Date elements to Date*/
    public static Date dateElementsToDate(String day, String month, String year, String hours, String minutes){
        String marker = "am";
        int h = Integer.parseInt(hours);
        if(h>12){
            h = h-12;
            marker = "pm";
        }
        String dateStr  = String.format(dateFormat, month, day, year, h, minutes, marker);
        return stringToDate(dateStr);
    }

    /** Convert String to Date with a given format*/
    public static Date stringToDate(String date, SimpleDateFormat formatter){
        try{
            return formatter.parse(date);
        }
        catch(ParseException p){
            return null;
        }
    }
    /** Convert Date to Short String*/
    public static String datetoShortString(Date dateTime){
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(dateTime);
    }

    /** Convert Date to Medium String*/
    public static String datetoMediumString(Date dateTime){
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(dateTime);
    }

    /** Breakdown date into day, month, year, hours and minutes */
    public static DateBreakDown breakdownDate(Date dateTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);

        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        String marker = String.valueOf(calendar.get(Calendar.AM_PM));
        if(marker.equalsIgnoreCase("1")){
            hours += 12;
        }
        return new DateBreakDown(month, day, year, String.valueOf(hours), String.valueOf(minutes));
    }
}
/** Class whose objects help breakdown Date into
 * month, day, year, hours and minutes */
class DateBreakDown{
    private final String month;
    private final String day;
    private final String year;
    private final String hours;
    private final String minutes;

    /** Parameterized constructor
     * @param month month of the date
     * @param day day of the date
     * @param year year of the date
     * @param hours hours of the date
     * @param minutes minutes of the date*/
    public DateBreakDown(String month, String day, String year, String hours, String minutes){
        this.month = month;
        this.day = day;
        this.year = year;
        this.hours = hours;
        this.minutes = minutes;
    }
    /** returns month
     * @return String month*/
    public String getMonth(){
        return this.month;
    }
    /** returns day
     * @return String day*/
    public String getDay(){
        return this.day;
    }
    /** returns year
     * @return String year*/
    public String getYear(){
        return this.year;
    }
    /** returns hours
     * @return String hours*/
    public String getHours(){
        return this.hours;
    }
    /** returns minutes
     * @return String minutes*/
    public String getMinutes(){
        return this.minutes;
    }
}