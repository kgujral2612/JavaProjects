package edu.pdx.cs410J.kgujral;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Airline class
 * */
public class Airline{
    /** The airline name */
    private final String name;
    /** The collection of flights inside an airline*/
    private Collection<Flight> flights;

    /**
     * Parameterized constructor to create
     * a new airline object with the specified name
     * assigns airline flights to an empty arraylist
     * @param    name    the name of the airline
     * */
    public Airline(String name) {
        this.name = name;
        this.flights = new ArrayList<>();
    }

    /**
     * @return airline name
     * */
    public String getName() {
        return this.name;
    }

    /**
     * @param flight append a flight object to the airline's flights collection
     * */
    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    /**
     * @return the collection of flights
     * */
    public Collection<Flight> getFlights() {
        return flights;
    }
}
