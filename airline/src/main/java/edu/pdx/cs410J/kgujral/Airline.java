package edu.pdx.cs410J.kgujral;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Airline class which implements the {@link AbstractAirline} abstract class
 * */
public class Airline extends AbstractAirline<Flight> {
  private final String name;
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
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * @param flight append a flight object to the airline's flights collection
   * */
  @Override
  public void addFlight(Flight flight) {
    flights.add(flight);
  }

  /**
   * @return the collection of flights
   * */
  @Override
  public Collection<Flight> getFlights() {
    return flights;
  }
}
