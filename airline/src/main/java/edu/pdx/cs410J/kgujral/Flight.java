package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AbstractFlight;

/**
 * The Flight class which implements the {@link AbstractFlight} abstract class
 * */
public class Flight extends AbstractFlight {
  /** The flight number */
  private final int number;
  /** The flight departure airport code */
  private final String src;
  /** The flight arrival airport code */
  private final String dest;
  /** The flight departure date and time*/
  private final String departure;
  /** The flight arrival date and time */
  private final String arrival;

  /** constructor that assigns default values to the data members */
  public Flight(){
    this.number = 42;
    this.src = "";
    this.dest = "";
    this.departure = "";
    this.arrival = "";
  }
  /** parameterized constructor that assigns the given values to data members
   * @param number the flight number
   * @param src the flight departure airport
   * @param departure the flight departure date and time
   * @param dest the flight arrival airport
   * @param arrival the flight arrival date and time
   * */
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
