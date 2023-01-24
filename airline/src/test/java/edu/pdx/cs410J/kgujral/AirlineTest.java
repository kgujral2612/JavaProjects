package edu.pdx.cs410J.kgujral;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Airline} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class AirlineTest {
    Airline sample_airline = new Airline("Kingfisher Airlines");
    Flight sample_flight = new Flight(3561, "DDN", "DDL", "05:00", "05:45");

    /*
     * Should create an airline
     * when an empty name is passed
     * */
    @Test
    void airlineIsCreatedSuccessfullyWithEmptyName(){
       Airline airline = new Airline("");
       assertThat(airline, is(not(equalTo(nullValue()))));
       assertThat(airline.getName(), is(equalTo("")));
   }

    /*
     * Should create an airline
     * when a valid name is passed
     * */
    @Test
    void airlineIsCreatedSuccessfullyWithName(){
       var name = "Air India";
       Airline airline = new Airline(name);
       assertThat(airline, is(not(equalTo(nullValue()))));
       assertThat(airline.getName(), is(equalTo(name)));
    }

    /*
    * Should return airline name
    * */
    @Test
    void getNameReturnsName(){
        assertThat(sample_airline.getName(), is("Kingfisher Airlines"));
    }

    /*
    * Should add flights to the collection
    * */
    @Test
    void addFlightAddsFlight(){
       //Arrange
       sample_airline.addFlight(sample_flight);

       //Act
       var flightCollection = sample_airline.getFlights();
       List flights = new ArrayList(flightCollection);

       //Assert
       assertThat(flights.size(), is(equalTo(1)));
       assertThat(flights.get(0), is(not(nullValue())));
       assertThat(((Flight)flights.get(0)).getNumber(), is(3561));
    }

    /*
     * Should get the updated flight collection
     * after adding flights
     * */
    @Test
    void getFlightsReturnsListOfFlights(){
        var airline = new Airline("My Airline");
        var myFlight = new Flight(1234, "my src", "my dest", "my departure", "my arrival");
        assertThat(airline.getFlights(), is(not(nullValue())));
        assertThat(airline.getFlights().size(), is(0));

        airline.addFlight(myFlight);
        assertThat(airline.getFlights().size(), is(1));

        airline.addFlight(myFlight);
        assertThat(airline.getFlights().size(), is(2));

    }

}
