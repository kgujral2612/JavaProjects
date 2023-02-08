package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit tests for the {@link TextDumper} class
 * */
public class TextDumperTest {

  /**If an airline only contains an airline name and no flights,
   * it should be successfully dumped onto the text file*/
  @Test
  void airlineNameIsDumpedInTextFormat() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(airline);

    String text = sw.toString();
    assertThat(text, containsString(airlineName));
  }

  /**If an airline only contains an airline name and multiple flights,
   * it should be successfully dumped onto the text file*/
  @Test
  void airlineAndFlightInfoIsDumped(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Indigo Airline";
    Airline airline = new Airline(airlineName);
    airline.addFlight(new Flight(6798, "DDN", "DDL", DateHelper.stringToDate("2/01/2022 06:15 pm"), DateHelper.stringToDate("2/01/2022 07:00 pm")));
    airline.addFlight(new Flight(5241, "DDN", "HYD", DateHelper.stringToDate("2/01/2022 08:23 am"), DateHelper.stringToDate("2/01/2022 11:53 am")));

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
    assertThat(read.getFlights().size(), equalTo(2));
    assertThat(read.getFlights().toArray()[0].toString(), equalTo("Flight 6798 departs DDN at 2/1/22, 6:15 PM arrives DDL at 2/1/22, 7:00 PM"));
    assertThat(read.getFlights().toArray()[1].toString(), equalTo("Flight 5241 departs DDN at 2/1/22, 8:23 AM arrives HYD at 2/1/22, 11:53 AM"));
  }

  /**The text dumped should be parsable*/
  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
  }
}
