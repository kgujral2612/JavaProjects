package edu.pdx.cs410J.kgujral;

import org.junit.jupiter.api.Test;

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
   @Test
    void airlineIsCreatedSuccessfullyWithEmptyName(){
       Airline airline = new Airline("");
       assertThat(airline, is(not(equalTo(nullValue()))));
       assertThat(airline.getName(), is(equalTo("")));
   }

    @Test
    void airlineIsCreatedSuccessfullyWithName(){
       var name = "Air India";
       Airline airline = new Airline(name);
       assertThat(airline, is(not(equalTo(nullValue()))));
       assertThat(airline.getName(), is(equalTo(name)));
    }

    @Test
    void getNameReturnsName(){
        assertThat(sample_airline.getName(), is("Kingfisher Airlines"));
    }

    @Test
    void addFlightAddsFlight(){}

    @Test
    void getFlightsReturnsListOfFlights(){}

}
