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
            prettyAirline.append(prettify(flight));
        }
        return prettyAirline.toString();
    }

    public static String prettify(Flight flight){
        StringBuilder prettyFlight = new StringBuilder();
        prettyFlight.append("Flight # ").append(flight.getNumber()).append("\n");
        prettyFlight.append("From ");
        prettyFlight.append(AirportNames.getName(flight.getSource().toUpperCase()));
        prettyFlight.append(" To ");
        prettyFlight.append(AirportNames.getName(flight.getDestination().toUpperCase())).append("\n");
        prettyFlight.append("Departs at: ");
        prettyFlight.append(DateHelper.datetoMediumString(flight.getDeparture())).append("\n");
        prettyFlight.append("Arrives at: ");
        prettyFlight.append(DateHelper.datetoMediumString(flight.getArrival())).append("\n");
        prettyFlight.append("Total Duration of Flight: ");
        prettyFlight.append((flight.getArrival().getTime() - flight.getDeparture().getTime()) / 60000).append(" minutes.\n");
        prettyFlight.append("------------------------------------------\n");
        return prettyFlight.toString();
    }
}
