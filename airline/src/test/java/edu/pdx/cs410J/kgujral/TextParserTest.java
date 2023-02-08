package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.ParserException;
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
  /** An invalid text file with a wrong format should not be
   * parsed successfully and should throw ParserException*/
  @Test
  void invalidFileCannotBeParsed() {
    InputStream resource = getClass().getResourceAsStream("invalid-airline-flight-info.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }
  /** A file that hasn't been created cannot be read.*/
  @Test
  void unavailableFileCannotBeParsed() {
    InputStream resource = getClass().getResourceAsStream("");
    assertThat(resource, notNullValue());
    var parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }
  /** Text should be read successfully from a valid file
   * @throws ParserException if the file can not be read*/
  @Test
  void readsTextFromValidFile() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("airline-flight-info.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    String content = parser.readText();
    assertNotNull(content);
  }

  /** Parser object cannot be created if reader is null
   */
  @Test
  void throwsExceptionIfReaderIsNull() {
    TextParser parser = new TextParser(null);
    assertThrows(NullPointerException.class, parser::readText);
  }

  @Test
  void invalidDepartureAirportCode(@TempDir File dir) throws IOException {
    File tempFile = new File(dir, "child.txt");
    TextDumper dumper = new TextDumper(new FileWriter(tempFile));
    Airline airline = new Airline("Sample Airline");
    Flight f1 = new Flight(648, "123", "SFO", DateHelper.stringToDate("3/3/2020 05:40 am"), DateHelper.stringToDate("3/3/2020 05:40 pm"));
    Flight f2 = new Flight(648, "ABQ", "SFO", DateHelper.stringToDate("3/3/2020 05:40 am"), DateHelper.stringToDate("3/3/2020 05:40 pm"));
    airline.addFlight(f1);
    airline.addFlight(f2);
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(tempFile));
    assertThrows(ParserException.class, parser::parse);
  }
  @Test
  void invalidArrivalAirportCode(@TempDir File dir) throws IOException {
    File tempFile = new File(dir, "child.txt");
    TextDumper dumper = new TextDumper(new FileWriter(tempFile));
    Airline airline = new Airline("Sample Airline");
    Flight f1 = new Flight(648, "ABQ", "123", DateHelper.stringToDate("3/3/2020 05:40 am"), DateHelper.stringToDate("3/3/2020 05:40 pm"));
    Flight f2 = new Flight(648, "ABQ", "SFO", DateHelper.stringToDate("3/3/2020 05:40 am"), DateHelper.stringToDate("3/3/2020 05:40 pm"));
    airline.addFlight(f1);
    airline.addFlight(f2);
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(tempFile));
    assertThrows(ParserException.class, parser::parse);
  }

  /** Airline should not be returned if file contains invalid time duration*/
  @Test
  void invalidFlightDuration() {
    InputStream resource = getClass().getResourceAsStream("invalid-flight-duration.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /** Airline should not be returned if file contains invalid time duration*/
  @Test
  void invalidTime(){
    InputStream resource = getClass().getResourceAsStream("invalid-flight-time.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /** Airline should not be returned if file contains departure invalid airport code*/
  @Test
  void invalidDepartureAirportCode(){
    InputStream resource = getClass().getResourceAsStream("invalid-departure-airport-code.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /** Airline should not be returned if file contains invalid airport code*/
  @Test
  void invalidArrivalAirportCode(){
    InputStream resource = getClass().getResourceAsStream("invalid-arrival-airport-code.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

}


