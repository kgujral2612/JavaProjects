package edu.pdx.cs410J.kgujral;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateHelper {
    /**Error message when date passed is invalid*/
    static String invalidDateMessage = "Unable to parse date because it is invalid. Was %s | Expected 12-hour date time format. eg: 2/5/2022 5:45pm";
   /** Convert String to Date*/
    public static Date stringToDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        return stringToDate(date, formatter);
    }
    /** Convert String to Date*/
    public static Date shortStringToDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");
        return stringToDate(date, formatter);
    }
    /** Convert String to Date with a given format*/
    public static Date stringToDate(String date, SimpleDateFormat formatter){
        try{
            return formatter.parse(date);
        }
        catch(ParseException p){
            System.err.println(String.format(invalidDateMessage, date));
        }
        return null;
    }

    /** Convert Date to Short String*/
    public static String datetoShortString(Date dateTime){
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(dateTime);
    }

    /** Convert Date to Short String Without Comma*/
    public static String dateToShortStringNoComma(Date dateTime){
        String dt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(dateTime);
        return dt.replace(",", "");
    }
}