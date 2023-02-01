package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {
  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Test Airline"));
  }

  @Test
  void validFileCanBeParsed() throws ParserException{
    InputStream resource = getClass().getResourceAsStream("airline-flight-info.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Alaska Airlines"));
    assertThat(airline.getFlights().size(), equalTo(2));
    assertThat(airline.getFlights().toArray()[0].toString(), equalTo("Flight 6791 departs PDX at 2/01/2023 19:00 arrives SFO at 2/01/2023 20:30"));
    assertThat(airline.getFlights().toArray()[1].toString(), equalTo("Flight 6792 departs SFO at 2/01/2023 17:23 arrives PDX at 2/01/2023 20:53"));
  }

  @Test
  void invalidFileCannotBeParsed() throws ParserException{
    InputStream resource = getClass().getResourceAsStream("invalid-airline-flight-info.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }
}


