package edu.pdx.cs410J.kgujral;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {
  /** a sample flight used for testing */
  Flight sample_flight = new Flight(3561, "DDN", "DDL", "03/03/2020 05:00", "03/03/2020 05:45");

  /**
   * When no values are passed while creating
   * an object of the flight class,
   * the default constructor is called
   */
  @Test
  void flightWithDefaultValuesWhenNoValuesAreProvided() {
    Flight flight = new Flight();
    assertThat(flight, is(not(equalTo(nullValue()))));
    assertThat(flight.getNumber(), equalTo(42));
    assertThat(flight.getSource(), equalTo(""));
    assertThat(flight.getDepartureString(), equalTo(""));
    assertThat(flight.getDestination(), equalTo(""));
    assertThat(flight.getArrivalString(), equalTo(""));
  }

   /** When values are passed while creating
   * an object of the flight class,
   * the parameterized constructor is called
   */
  @Test
  void flightWhenValuesAreProvided() {
    int number = 123;
    String src = "PDX";
    String dest = "SFO";
    String departure = "03/03/2020 16:00";
    String arrival = "03/03/2020 19:00";
    Flight flight = new Flight(number, src, dest, departure, arrival);
    assertThat(flight, is(not(equalTo(nullValue()))));
    assertThat(flight.getNumber(), equalTo(number));
    assertThat(flight.getSource(), equalTo(src));
    assertThat(flight.getDestination(), equalTo(dest));
    assertThat(flight.getDepartureString(), equalTo(departure));
    assertThat(flight.getArrivalString(), equalTo(arrival));
  }

  /** getNumber should return the flight number */
  @Test
  void getNumberReturnsNumber(){
    assertThat(sample_flight.getNumber(), is(3561));}

  /** getSource should return the flight departure airport code */
  @Test
  void getSourceReturnsSource(){
    assertThat(sample_flight.getSource(), is("DDN"));
  }

  /** getDeparture should return the flight departure date and time */
  @Test
  void getDepartureStringReturnsDepartureString(){
    assertThat(sample_flight.getDepartureString(), is("03/03/2020 05:00"));
  }
  /** getDestination should return the flight destination airport code */
  @Test
  void getDestinationReturnsDestination(){
    assertThat(sample_flight.getDestination(), is("DDL"));
  }

  /** getArrival should return the flight arrival date and time */
  @Test
  void getArrivalStringReturnsArrivalString(){
    assertThat(sample_flight.getArrivalString(), is("03/03/2020 05:45"));
  }

  /** toString should return the flight details in form of a string */
  @Test
  void toStringReturnsValidOutput() {
    Flight flight = new Flight();
    var op = flight.toString();
    assertThat(op, containsString("Flight 42 departs  at  arrives  at "));
  }
}
