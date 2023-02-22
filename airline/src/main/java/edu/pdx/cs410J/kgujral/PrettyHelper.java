package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AirportNames;
import java.util.Arrays;

/** This class creates a pretty representation of an airline*/
public class PrettyHelper {
    /** Returns a string with prettified airline info*/
    public static String prettify(Airline airline){
        StringBuilder prettyAirline = new StringBuilder();
        prettyAirline.append(airline.getName()).append("\n");
        Flight[] flights = airline.getFlights().toArray(new Flight[0]);
        Arrays.sort(flights);
        for(var flight: flights){

            prettyAirline.append("Flight # ").append(flight.getNumber()).append("\n");
            prettyAirline.append("From ");
            prettyAirline.append(AirportNames.getName(flight.getSource().toUpperCase()));
            prettyAirline.append(" To ");
            prettyAirline.append(AirportNames.getName(flight.getDestination().toUpperCase())).append("\n");
            prettyAirline.append("Departs at: ");
            prettyAirline.append(DateHelper.datetoMediumString(flight.getDeparture())).append("\n");
            prettyAirline.append("Arrives at: ");
            prettyAirline.append(DateHelper.datetoMediumString(flight.getArrival())).append("\n");
            prettyAirline.append("Total Duration of Flight: ");
            prettyAirline.append((flight.getArrival().getTime() - flight.getDeparture().getTime()) / 60000).append(" minutes.\n");
            prettyAirline.append("------------------------------------------\n");
        }
        return prettyAirline.toString();
    }
}
