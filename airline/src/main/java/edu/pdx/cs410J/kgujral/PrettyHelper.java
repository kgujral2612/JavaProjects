package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AirportNames;
import java.util.Arrays;

/** This class creates a pretty representation of an airline*/
public class PrettyHelper {
    public static String prettify(Airline airline){
        String prettyAirline = "";
        prettyAirline += airline.getName() + "\n";
        Flight[] flights = airline.getFlights().toArray(new Flight[0]);
        Arrays.sort(flights);
        for(var flight: flights){

            prettyAirline += "Flight # " + flight.getNumber() + "\n";
            prettyAirline += "From ";
            prettyAirline += AirportNames.getName(flight.getSource().toUpperCase());
            prettyAirline += " To ";
            prettyAirline += AirportNames.getName(flight.getDestination().toUpperCase()) + "\n";
            prettyAirline += "Departs at: ";
            prettyAirline += DateHelper.datetoMediumString(flight.getDeparture()) + "\n";
            prettyAirline += "Arrives at: ";
            prettyAirline += DateHelper.datetoMediumString(flight.getArrival()) + "\n";
            prettyAirline += "Total Duration of Flight: ";
            prettyAirline += (flight.getArrival().getTime() - flight.getDeparture().getTime())/60000 + " minutes.\n";
            prettyAirline += "------------------------------------------\n";
        }
        return prettyAirline;
    }
}
