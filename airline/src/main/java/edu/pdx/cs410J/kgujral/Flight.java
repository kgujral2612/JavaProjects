package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AbstractFlight;

/**
 * The Flight class which implements the {@link AbstractFlight} abstract class
 * */
public class Flight extends AbstractFlight {
  private final int number;
  private final String src;
  private final String dest;
  private final String departure;
  private final String arrival;

  public Flight(){
    this.number = 42;
    this.src = "";
    this.dest = "";
    this.departure = "";
    this.arrival = "";
  }
  public Flight(int number, String src, String dest, String departure, String arrival){
    this.number = number;
    this.src = src;
    this.dest = dest;
    this.departure = departure;
    this.arrival = arrival;
  }

  /**
   * @return flight number
   * */
  @Override
  public int getNumber() {
    return this.number;
  }

  /**
   * @return source for the flight
   * */
  @Override
  public String getSource() {
    return this.src;
  }

  /**
  * @return departure time for the flight
  * */
  @Override
  public String getDepartureString() {
    return this.departure;
  }

  /**
   * @return destination for the flight
   * */
  @Override
  public String getDestination() {
    return this.dest;
  }

  /**
   * @return arrival time for the flight
   * */
  @Override
  public String getArrivalString() {
    return this.arrival;
  }
}
