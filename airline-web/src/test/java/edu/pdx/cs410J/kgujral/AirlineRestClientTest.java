package edu.pdx.cs410J.kgujral;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * A unit test for the REST client that demonstrates using mocks and
 * dependency injection
 * Tests the get methods
 */
public class AirlineRestClientTest {


  /**
   * Should get airline and flight info from the client
   * @throws ParserException when the airline info cannot be parsed
   * @throws IOException when the file cannot be accessed
   * */
  @Test
  void getAirlineAndFlightInfoFromClient() throws ParserException, IOException {
    Airline airline = new Airline("Test Airways");
    airline.addFlight(new Flight(1654, "SFO", "BER",
            DateHelper.stringToDate("03/26/2020 05:00 pm"), DateHelper.stringToDate("03/27/2020 06:15 am")));
    airline.addFlight(new Flight(9867, "ATL", "JAX",
            DateHelper.stringToDate("11/19/2020 04:35 pm"), DateHelper.stringToDate("11/20/2020 02:15 am")));

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(Map.of(AirlineServlet.AIRLINE_NAME, airline.getName()))).thenReturn(airlineAsText(airline));
    
    AirlineRestClient client = new AirlineRestClient(http);

    Airline airlineFromClient = client.getAllFlights(airline.getName());
    assertNotNull(airlineFromClient);
    assertThat(airlineFromClient.toString(), equalTo(airline.toString()));
    assertThat(airlineFromClient.getFlights().toArray().length, is(2));
  }

  /**
   * Should get flight info by src and dest from the client
   * @throws ParserException when the airline info cannot be parsed
   * @throws IOException when the file cannot be accessed
   * */
  @Test
  void getFlightsBySrcAndDestFromClient() throws ParserException, IOException {
    Airline airline = new Airline("Test Airways");
    airline.addFlight(new Flight(1111, "SFO", "BER",
            DateHelper.stringToDate("03/26/2020 05:00 pm"), DateHelper.stringToDate("03/27/2020 06:15 am")));
    airline.addFlight(new Flight(2222, "ATL", "JAX",
            DateHelper.stringToDate("11/19/2020 04:35 pm"), DateHelper.stringToDate("11/20/2020 02:15 am")));
    airline.addFlight(new Flight(3333, "ATL", "PDX",
            DateHelper.stringToDate("11/19/2020 02:35 am"), DateHelper.stringToDate("11/20/2020 02:15 pm")));

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(Map.of(AirlineServlet.AIRLINE_NAME, airline.getName()))).thenReturn(airlineAsText(airline));
    AirlineRestClient client = new AirlineRestClient(http);

    Collection<Flight> flights= client.getFlightsBySrcAndDest("Test Airways", "ATL", "PDX");
    assertNotNull(flights);
    assertThat(flights.toArray().length, is(1));
  }

  /**Utility function
   * @param  airline airline to be converted to text
   * @returns response with airline as string*/
  private HttpRequestHelper.Response airlineAsText(Airline airline) {
    StringWriter writer = new StringWriter();
    new TextDumper(writer).dump(airline);

    return new HttpRequestHelper.Response(writer.toString());
  }

}
