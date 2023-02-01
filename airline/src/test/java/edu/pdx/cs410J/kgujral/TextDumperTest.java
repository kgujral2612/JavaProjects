package edu.pdx.cs410J.kgujral;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

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
  @Test
  void airlineAndFlightInfoIsDumped(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Indigo Airline";
    Airline airline = new Airline(airlineName);
    airline.addFlight(new Flight(6798, "DDN", "DDL", "2/01/2022 06:15", "2/01/2022 07:00"));
    airline.addFlight(new Flight(5241, "DDN", "HYD", "2/01/2022 08:23", "2/01/2022 11:53"));

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
    assertThat(read.getFlights().size(), equalTo(2));
    assertThat(read.getFlights().toArray()[0].toString(), equalTo("Flight 6798 departs DDN at 2/01/2022 06:15 arrives DDL at 2/01/2022 07:00"));
    assertThat(read.getFlights().toArray()[1].toString(), equalTo("Flight 5241 departs DDN at 2/01/2022 08:23 arrives HYD at 2/01/2022 11:53"));
  }

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
