package edu.pdx.cs410J.kgujral;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final HttpRequestHelper http;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
      this.http = http;
    }


   /** Returns all flights of an airline
    * @throws ParserException when the airline info cannot be parsed
    * @throws IOException when the file cannot be accessed
    * */
  public Airline getAllFlights(String airlineName) throws IOException, ParserException {
      Response response = http.get(Map.of(AirlineServlet.AIRLINE_NAME, airlineName));
      if(response == null || response.getContent() == null)
          return null;
      throwExceptionIfNotOkayHttpStatus(response);

      TextParser parser = new TextParser(new StringReader(response.getContent()));
      return parser.parse();
  }

  /** Returns all flights with a given src and dest
   * @throws ParserException when the airline info cannot be parsed
   * @throws IOException when the file cannot be accessed
   * */
  public Collection<Flight> getFlightsBySrcAndDest(String airlineName, String src, String dest) throws IOException, ParserException{
      Response response = http.get(Map.of(AirlineServlet.AIRLINE_NAME, airlineName));
      if(response == null || response.getContent() == null)
          return null;
      throwExceptionIfNotOkayHttpStatus(response);

      TextParser parser = new TextParser(new StringReader(response.getContent()));
      Airline airline = parser.parse();
      Collection<Flight> flights = new ArrayList<>();
      for (var flight : airline.getFlights()) {
          if(flight.getSource().equals(src) && flight.getDestination().equals(dest)){
              flights.add(flight);
          }
      }
      return flights;
  }

  public void addFlight(String airline, Flight flight) throws IOException {
      Response response = http.post(Map.of(AirlineServlet.AIRLINE_NAME, airline,
              AirlineServlet.FLIGHT_NUM, String.valueOf(flight.getNumber()),
              AirlineServlet.SRC, flight.getSource(), AirlineServlet.DEST, flight.getDestination(),
              AirlineServlet.DEPART, flight.getDepartureString(), AirlineServlet.ARRIVE, flight.getArrivalString()));
      throwExceptionIfNotOkayHttpStatus(response);
  }

  private void throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getHttpStatusCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
  }
}
