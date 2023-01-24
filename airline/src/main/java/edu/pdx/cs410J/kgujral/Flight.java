package edu.pdx.cs410J.kgujral;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {
  int number;
  String src;
  String dest;
  String departure;
  String arrival;

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
  @Override
  public int getNumber() {
    return this.number;
  }

  @Override
  public String getSource() {
    return this.src;
  }

  @Override
  public String getDepartureString() {
    return this.departure;
  }

  @Override
  public String getDestination() {
    return this.dest;
  }

  @Override
  public String getArrivalString() {
    return this.arrival;
  }
}
