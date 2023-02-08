package edu.pdx.cs410J.kgujral;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateHelper {
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
