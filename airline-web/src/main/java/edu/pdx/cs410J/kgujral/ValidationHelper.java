package edu.pdx.cs410J.kgujral;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationHelper {

    /**
     * Returns true if the airlineName is valid,
     * i.e, it should not be null, empty or blank
     * @param  airlineName  the name of an airline that needs to be validated
     * @return              true if airline is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidAirlineName(String airlineName){
        return airlineName != null && !airlineName.isEmpty() && !airlineName.isBlank();
    }

    /**
     * Returns true if the flightNumber is valid,
     * i.e, it should be numeric
     * @param  flightNumber  a flight's number that needs to be validated
     * @return               true if flightNumber is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidFlightNumber(String flightNumber){
        try{
            int num = Integer.parseInt(flightNumber);
            return num >= 0;
        }
        catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Returns true if the airportCode is valid,
     * i.e, it should consist of letters and should be of length 3
     * @param  airportCode  airport code that needs to be validated
     * @return              true if airportCode is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidAirportCode(String airportCode){
        if(airportCode == null)
            return false;
        for(int i=0; i<airportCode.length(); i++){
            if (!Character.isLetter(airportCode.charAt(i)))
                return false;
        }
        return airportCode.length() == 3;
    }

    /**
     * Returns true if the airportCode has a corresponding airportName,
     * @param  airportCode  airport code that needs to be validated
     * @return              true if airportCode is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidAirportName(String airportCode){
        if(airportCode == null)
            return false;
        return AirportNames.getName(airportCode.toUpperCase())!=null;
    }
    /**
     * Returns true if the date is valid,
     * i.e, in the format of mm/dd/yyyy
     * @param  date  a string consisting of date
     * @return  true if date is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidDate(String date){
        if(date == null || date.isEmpty())
            return false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try{
            dateFormat.parse(date);
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }
    @VisibleForTesting
    static boolean isValidTimeMarker(String mm){
        return(mm.compareToIgnoreCase("am")==0) || (mm.compareToIgnoreCase("pm") == 0);
    }

    /**
     * Returns true if the time is valid,
     * i.e, in the format of hh:mm
     * @param  time  contains time
     * @return  true if date is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidTime(String time){
        if(time == null || time.isEmpty())
            return false;
        try{
            String[] splitT = time.split(":");
            if(splitT.length != 2)
                return false;
            int hours = Integer.parseInt(splitT[0]);
            int minutes = Integer.parseInt(splitT[1]);
            return hours <= 12 && hours >= 0 && minutes <= 59 && minutes >= 0;
        }
        catch (Exception e){
            return false;
        }
    }

    @VisibleForTesting
    static boolean isValidFlightDuration(Date departure, Date arrival){
        if(departure == null || arrival == null)
            return false;
        return arrival.getTime() - departure.getTime() > 0;
    }
}
