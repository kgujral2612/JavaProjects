package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TextParser} class
 */
public class TextParserTest {

    /** A valid text file with a correct format should be parsed successfully*/
    @Test
    void validTextFileCanBeParsed() throws ParserException {
        InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
        assertThat(resource, notNullValue());
        TextParser parser = new TextParser(new InputStreamReader(resource));
        Airline airline = parser.parse();
        assertThat(airline.getName(), equalTo("Test Airline"));
    }
    /** A valid text file with a correct format
     * and multiple flights should be parsed successfully
     * @throws ParserException if file is not present
     * */
    @Test
    void validFileCanBeParsed() throws ParserException{
        InputStream resource = getClass().getResourceAsStream("airline-flight-info.txt");
        assertThat(resource, notNullValue());
        TextParser parser = new TextParser(new InputStreamReader(resource));
        Airline airline = parser.parse();
        assertThat(airline.getName(), equalTo("Alaska Airlines"));
        assertThat(airline.getFlights().size(), equalTo(2));
        assertThat(airline.getFlights().toArray()[0].toString(), equalTo("Flight 6791 departs PDX at 1/12/23, 9:00 PM arrives SFO at 2/12/23, 2:30 AM"));
        assertThat(airline.getFlights().toArray()[1].toString(), equalTo("Flight 6792 departs SFO at 1/2/23, 7:23 AM arrives PDX at 1/2/23, 2:53 PM"));
    }
    /** An empty text file should
     * be parsed successfully
     * @throws ParserException if file is not present*/
    @Test
    void emptyTextFileCanBeParsed()throws ParserException{
        InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
        assertThat(resource, notNullValue());
        TextParser parser = new TextParser(new InputStreamReader(resource));
        assertNull(parser.parse());
    }
}


