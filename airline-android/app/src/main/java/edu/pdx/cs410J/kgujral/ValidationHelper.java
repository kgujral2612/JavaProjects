package edu.pdx.cs410J.kgujral;
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
    static boolean isValidAirlineName(String airlineName){
        return airlineName != null && !airlineName.isEmpty() ;
    }

    /**
     * Returns true if the flightNumber is valid,
     * i.e, it should be numeric
     * @param  flightNumber  a flight's number that needs to be validated
     * @return               true if flightNumber is valid, false otherwise
     * */
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
    static boolean isValidDateTime(String date){
        if(date == null || date.isEmpty())
            return false;

        return DateHelper.stringToDate(date) !=null;
    }

    static boolean isValidFlightDuration(String departure, String arrival){
        Date dep = DateHelper.stringToDate(departure);
        Date arr = DateHelper.stringToDate(arrival);
        if(departure == null || arrival == null)
            return false;
        return arr.getTime() - dep.getTime() > 0;
    }
}
