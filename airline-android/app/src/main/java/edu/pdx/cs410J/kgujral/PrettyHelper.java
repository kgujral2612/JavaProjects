package edu.pdx.cs410J.kgujral;
import java.util.Arrays;
import java.util.Date;

/** This class creates a pretty representation of an airline*/
public class PrettyHelper {
    public static String prettyAirportName(String airport){
        return AirportNames.getName(airport.toUpperCase());
    }

    public static String prettyDate(Date date){
        return DateHelper.datetoMediumString(date);
    }

    public static String prettyDuration(Date arrival, Date departure){
        return ((arrival.getTime() - departure.getTime()) / 60000.0) + " minutes";
    }
}
