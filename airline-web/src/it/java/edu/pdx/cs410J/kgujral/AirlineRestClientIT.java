package edu.pdx.cs410J.kgujral;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@TestMethodOrder(MethodName.class)
class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  /** Creates a new client */
  private AirlineRestClient newClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

  /** Should post new airline & flight info to the server */
  @Test
  void shouldAddNewAirlineInfo() throws IOException, ParserException {
    var client = newClient();
    String airlineName = "Test Airways";

    String flightNum = "68445";
    String src = "PDX";
    String dest = "SFO";
    String depart = "03/03/2020 05:00 pm";
    String arrive = "03/03/2020 07:30 pm";

    Flight flight = new Flight(Integer.parseInt(flightNum),
            src, dest, DateHelper.stringToDate(depart), DateHelper.stringToDate(arrive));

    client.addFlight(airlineName, flight);
    Airline airline = client.getAllFlights(airlineName);
    assertThat(airline.getName(), equalTo(airlineName));
    assertEquals(airline.getFlights().toArray().length, 1);
  }

  /** Should post new flight info for an existing airline to the server*/
  @Test
  void shouldAddNewFlightInfo(){}


  /** Should issue an error when the user wants to add new flight info
   * for an existing airline that does not exist*/
  @Test
  void shouldIssueErrorWhenAirlineDoesNotExist(){

  }
}