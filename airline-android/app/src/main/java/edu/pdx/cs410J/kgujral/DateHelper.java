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

}