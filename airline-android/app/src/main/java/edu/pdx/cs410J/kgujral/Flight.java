package edu.pdx.cs410J.kgujral;
import java.io.Serializable;
import java.util.Date;

/**
 * The Flight class
 * */
public class Flight implements Comparable<Flight>, Serializable {
    /** The flight number */
    private final int number;
    /** The flight departure airport code */
    private final String src;
    /** The flight arrival airport code */
    private final String dest;
    /** The flight departure date and time*/
    private final Date departure;
    /** The flight arrival date and time */
    private final Date arrival;

    /** constructor that assigns default values to the data members */
    public Flight(){
        this.number = 42;
        this.src = "";
        this.dest = "";
        this.departure = new Date();
        this.arrival = new Date();
    }
    /** parameterized constructor that assigns the given values to data members
     * @param number the flight number
     * @param src the flight departure airport
     * @param departure the flight departure date and time
     * @param dest the flight arrival airport
     * @param arrival the flight arrival date and time
     * */
    public Flight(int number, String src, String dest, Date departure, Date arrival){
        this.number = number;
        this.src = src;
        this.dest = dest;
        this.departure = departure;
        this.arrival = arrival;
    }

    /**
     * @return flight number
     * */
    public int getNumber() {
        return this.number;
    }

    /**
     * @return source for the flight
     * */
    public String getSource() {
        return this.src;
    }

    /**
     * @return departure time for the flight in form of a string
     * */
    public String getDepartureString() {
        return DateHelper.datetoShortString(this.departure);
    }

    /**
     * @return departure time for the flight in a Date object
     * */
    public Date getDeparture() {
        return this.departure;
    }

    /**
     * @return destination for the flight
     * */
    public String getDestination() {
        return this.dest;
    }

    /**
     * @return arrival time for the flight in form of a string
     * */
    public String getArrivalString() {
        return DateHelper.datetoShortString(this.arrival);
    }

    /**
     * @return arrival time for the flight inside a Date object
     * */
    public Date getArrival() {
        return this.arrival;
    }

    public int compareTo(Flight o) {
        int diff = this.src.compareTo(o.src);
        return diff != 0? diff : (int) (this.getDeparture().getTime() - o.getDeparture().getTime());
    }
}
