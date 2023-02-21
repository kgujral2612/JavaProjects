package edu.pdx.cs410J.kgujral;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;

/** Unit tests for {@link XmlParser class} */
public class XmlParserTest {
    @Test
    void shouldParseValidXml(){
        File file = new File("/Users/kaushambigujral/Desktop/git/PSUWinter23KG/airline/src/test/resources/edu/pdx/cs410J/kgujral/valid-airline.xml");
        XmlParser parser = new XmlParser(file);
        Airline airline = parser.parse();

        assertThat(airline.toString(), containsString("Valid Airlines with 2 flights"));
        assertThat(airline.getFlights().size(), equalTo(2));
        assertThat(airline.getFlights().toArray()[0].toString(),
                equalTo("Flight 1437 departs BJX at 9/25/20, 5:00 PM arrives CMN at 9/26/20, 3:56 AM"));
        assertThat(airline.getFlights().toArray()[1].toString(),
                equalTo("Flight 7865 departs JNB at 5/15/20, 7:24 AM arrives XIY at 5/16/20, 9:07 AM"));
    }

    @Test
    void shouldNotParseInValidXml(){
        File file = new File("/Users/kaushambigujral/Desktop/git/PSUWinter23KG/airline/src/test/resources/edu/pdx/cs410J/kgujral/invalid-airline.xml");
        XmlParser parser = new XmlParser(file);
        Airline airline = parser.parse();
        assertNull(airline);
    }
}
