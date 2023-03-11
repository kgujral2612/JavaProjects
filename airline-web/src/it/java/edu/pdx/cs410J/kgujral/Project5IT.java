package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */

@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {

    /** When the args contains -README option,
     * Readme should be printed and the
     * program should exit*/
    @Test
    void userInputWithReadme(){
        String[] args = new String[]{"-host", "localhost", "-README", "-port", "8080", "Test Airline", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        MainMethodResult result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
        assertThat(result.getTextWrittenToStandardOut(), containsString("CS501 Project 5::  A REST-ful Airline Web Service"));
    }

    /**
     * When no args are provided,
     * program usage should be printed
     * */
    @Test
    void printUsage(){
        String[] args = new String[]{};
        MainMethodResult result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("You must provide some arguments to interact with the program"));
    }


    /** When a data element is invalid,
     * should not post info to the serve*/
    @Test
    void shouldNotPostNewFlightInfoWithInvalidArgs(){
        //Invalid Flight Number
        String[] args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "Flight345", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        MainMethodResult result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Flight Number"));

        //Invalid Src
        args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "345", "Portland", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Source"));

        //Invalid Departure Date
        args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "345", "PDX", "17/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Departure Date"));

        //Invalid Departure Time
        args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "345", "PDX", "07/19/2023", "19:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Departure Time"));

        //Invalid Departure Time Marker
        args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "345", "PDX", "07/19/2023", "1:02", "px", "ORD", "07/19/2023", "6:22", "pm"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Departure Time Marker"));

        //Invalid Dest
        args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "345", "PDX", "07/19/2023", "1:02", "pm", "Orlando", "07/19/2023", "6:22", "pm"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Destination"));

        //Invalid Arrival Date
        args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "345", "PDX", "07/19/2023", "1:02", "pm", "ORD", "19/19/2023", "6:22", "pm"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Arrival Date"));

        //Invalid Arrival Time
        args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "345", "PDX", "07/19/2023", "1:02", "pm", "ORD", "9/19/2023", "16:22", "pm"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Arrival Time"));

        //Invalid Arrival Time Marker
        args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "345", "PDX", "07/19/2023", "1:02", "pm", "ORD", "9/19/2023", "6:22", "xm"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Arrival Time Marker"));

        //Invalid Port Number
        args = new String[]{"-host", "localhost", "-port", "#8080",
                "Test Airline", "345", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Port Number"));

        //Invalid search src
        args = new String[]{"-host", "localhost", "-port", "8080", "-search", "Test Airline", "PDX1", "SFO"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Source for searching"));

        //Invalid search dest
        args = new String[]{"-host", "localhost", "-port", "8080", "-search", "Test Airline", "PDX", "SFO1"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Destination for searching"));

        //Missing Destination in Search
        args = new String[]{"-host", "localhost", "-port", "8080", "-search", "Test Airline", "PDX"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid argument Destination for searching . Given: null"));
    }

    /** When all args are valid,
     * should post info to the serve*/
   @Test
   void shouldPostNewFlightInfoToServer() throws IOException, ParserException {
       //arrange
       String airlineName = "Test Airline";
       String flightNumber = "3453";
       String src = "PDX";
       String depDate = "07/19/2023"; String depTime = "1:02"; String depTimeMarker = "am";
       String dest = "SFO";
       String arrDate = "07/19/2023"; String arrTime = "11:02"; String arrTimeMarker = "pm";
       String[] args = new String[]{"-host", "localhost", "-port", "8080",
               airlineName, flightNumber, src, depDate, depTime, depTimeMarker, dest, arrDate, arrTime, arrTimeMarker};

       Flight flight = new Flight(Integer.parseInt(flightNumber),
               src, dest,
               DateHelper.stringToDate(depDate + " " + depTime + " " + depTimeMarker),
               DateHelper.stringToDate(arrDate + " " + arrTime + " " + arrTimeMarker));
       Airline airline = new Airline(airlineName);
       airline.addFlight(flight);

       //act
       MainMethodResult result = invokeMain( Project5.class, args);

       //assert
       assertThat(result.getTextWrittenToStandardOut(), containsString(Messages.addedFlightTo("3453","Test Airline")));
   }

   /** Should print description of the new flight */
   @Test
   void shouldPrintAirlineInfo() {
       //arrange
       String airlineName = "Test Airline";
       String flightNumber = "3453";
       String src = "PDX";
       String depDate = "07/19/2023"; String depTime = "1:02"; String depTimeMarker = "am";
       String dest = "SFO";
       String arrDate = "07/19/2023"; String arrTime = "11:02"; String arrTimeMarker = "pm";
       String[] args = new String[]{"-host", "localhost", "-port", "8080", "-print",
               airlineName, flightNumber, src, depDate, depTime, depTimeMarker,
               dest, arrDate, arrTime, arrTimeMarker};

       Flight flight = new Flight(Integer.parseInt(flightNumber),
               src, dest,
               DateHelper.stringToDate(depDate + " " + depTime + " " + depTimeMarker),
               DateHelper.stringToDate(arrDate + " " + arrTime + " " + arrTimeMarker));
       Airline airline = new Airline(airlineName);
       airline.addFlight(flight);

       //act
       MainMethodResult result = invokeMain( Project5.class, args);

       //assert
       assertThat(result.getTextWrittenToStandardError(), equalTo(""));
       assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 3453 departs PDX at 7/19/23, 1:02 AM arrives SFO at 7/19/23, 11:02 PM"));
   }

    /** Should search by src and dest
     * and pretty print on std */
    @Test
    void shouldSearchFlightsBySrcDest(){
        // add an airline info
        String airlineName = "Test Airline";
        String flightNumber = "8888";
        String src = "ORD";
        String depDate = "07/19/2023"; String depTime = "1:02"; String depTimeMarker = "am";
        String dest = "BER";
        String arrDate = "07/19/2023"; String arrTime = "11:02"; String arrTimeMarker = "pm";
        String[] args = new String[]{"-host", "localhost", "-port", "8080",
                airlineName, flightNumber, src, depDate, depTime, depTimeMarker, dest, arrDate, arrTime, arrTimeMarker};

        Flight flight = new Flight(Integer.parseInt(flightNumber),
                src, dest,
                DateHelper.stringToDate(depDate + " " + depTime + " " + depTimeMarker),
                DateHelper.stringToDate(arrDate + " " + arrTime + " " + arrTimeMarker));
        Airline airline = new Airline(airlineName);
        airline.addFlight(flight);
        invokeMain( Project5.class, args);

        //when flight info exists
        args = new String[]{"-host", "localhost", "-port", "8080", "-print", "-search", "Test Airline", "ORD", "BER"};
        MainMethodResult result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Test Airline"));

        // when flight info does not exist
        args = new String[]{"-host", "localhost", "-port", "8080", "-search", "Test Airline", "BOM", "BJX"};
        result = invokeMain( Project5.class, args);

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
        assertThat(result.getTextWrittenToStandardOut(), containsString(Messages.noFlights("BOM", "BJX", "Test Airline")));
    }

    /** Should search by airline
     * and pretty print on std */
    @Test
    void shouldSearchFlightsByAirline() {
        // add an airline info
        String airlineName = "Test Airline";
        String flightNumber = "3453";
        String src = "PDX";
        String depDate = "07/19/2023"; String depTime = "1:02"; String depTimeMarker = "am";
        String dest = "SFO";
        String arrDate = "07/19/2023"; String arrTime = "11:02"; String arrTimeMarker = "pm";
        String[] args = new String[]{"-host", "localhost", "-port", "8080",
                airlineName, flightNumber, src, depDate, depTime, depTimeMarker, dest, arrDate, arrTime, arrTimeMarker};

        Flight flight = new Flight(Integer.parseInt(flightNumber),
                src, dest,
                DateHelper.stringToDate(depDate + " " + depTime + " " + depTimeMarker),
                DateHelper.stringToDate(arrDate + " " + arrTime + " " + arrTimeMarker));
        Airline airline = new Airline(airlineName);
        airline.addFlight(flight);
        invokeMain( Project5.class, args);

        // search an airline info
        args = new String[]{"-host", "localhost", "-port", "8080", "-search", "Test Airline"};
        MainMethodResult result = invokeMain( Project5.class, args);

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Test Airline"));
    }

    /**When no airline is on the server, an error message should be issued for the user*/
    @Test
    void shouldIssueErrorWhenAirlineInfoIsNotOnServer(){
        String[] args = new String[]{"-host", "localhost", "-port", "8080", "-search", "Test Airway"};
        var result = invokeMain( Project5.class, args);

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
        assertThat(result.getTextWrittenToStandardOut(), containsString("The airline Test Airway is not present on the server"));

        args = new String[]{"-search","-host", "localhost", "-port", "8080", "Test Airway", "PDX", "SFO"};
        result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
        assertThat(result.getTextWrittenToStandardOut(), containsString("The airline Test Airway is not present on the server"));
    }

    /**When airline name mismatches, a message should be issued */
    @Test
    void shouldIssueErrorWhenAirlineInfoMismatches(){
        // add an airline info
        String airlineName = "Test Airline";
        String flightNumber = "3453";
        String src = "PDX";
        String depDate = "07/19/2023"; String depTime = "1:02"; String depTimeMarker = "am";
        String dest = "SFO";
        String arrDate = "07/19/2023"; String arrTime = "11:02"; String arrTimeMarker = "pm";
        String[] args = new String[]{"-host", "localhost", "-port", "8080",
                airlineName, flightNumber, src, depDate, depTime, depTimeMarker, dest, arrDate, arrTime, arrTimeMarker};

        Flight flight = new Flight(Integer.parseInt(flightNumber),
                src, dest,
                DateHelper.stringToDate(depDate + " " + depTime + " " + depTimeMarker),
                DateHelper.stringToDate(arrDate + " " + arrTime + " " + arrTimeMarker));
        Airline airline = new Airline(airlineName);
        airline.addFlight(flight);
        invokeMain( Project5.class, args);

        args = new String[]{"-host", "localhost", "-search", "-port", "8080", "TestAirway"};
        MainMethodResult result = invokeMain( Project5.class, args);

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
        assertThat(result.getTextWrittenToStandardOut(), containsString("The airline stored on the server is different from what you provided. Given: TestAirway | Expected: Test Airline\n"));
    }
}