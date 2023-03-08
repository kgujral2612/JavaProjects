package edu.pdx.cs410J.kgujral;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
    static final String AIRLINE_NAME = "airline";
    static final String FLIGHT_NUM = "flightNumber";
    static final String SRC = "src";
    static final String DEST = "dest";
    static final String DEPART = "depart";
    static final String ARRIVE = "arrive";

  private Airline airline = null;

  /**
   * Handles an HTTP GET request from a client by writing the definition of the
   * word specified in the "word" HTTP parameter to the HTTP response.  If the
   * "word" parameter is not specified, all of the entries in the dictionary
   * are written to the HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airline = getParameter(AIRLINE_NAME, request);
      if(airline != null){
        writeAllFlightInfo(response);
      }
  }

  /**
   * Handles an HTTP POST request by storing the dictionary entry for the
   * "word" and "definition" request parameters.  It writes the dictionary
   * entry to the HTTP response.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airline = getParameter(AIRLINE_NAME, request);

      String flightNumber = getParameter(FLIGHT_NUM, request);

      if(flightNumber == null) {
          missingRequiredParameter(response, FLIGHT_NUM);
          return;
      }
      String src = getParameter(SRC, request);

      if(src == null){
          missingRequiredParameter(response, SRC);
          return;
      }

      String dest = getParameter(DEST, request);
      if(dest == null){
          missingRequiredParameter(response, DEST);
          return;
      }

      String depart = getParameter(DEPART, request);
      if(depart == null){
          missingRequiredParameter(response, DEPART );
          return;
      }

      String arrive = getParameter(ARRIVE, request);
      if (arrive == null) {
          missingRequiredParameter(response, ARRIVE);
          return;
      }

      if(this.airline == null){
          if(airline == null){
              missingRequiredParameter(response, AIRLINE_NAME);
              return;
          }
          this.airline = new Airline(airline);
      }

      Flight flight = new Flight(Integer.parseInt(flightNumber),
                            src, dest, DateHelper.shortStringToDate(depart),
                                DateHelper.shortStringToDate(arrive));
      this.airline.addFlight(flight);

      PrintWriter pw = response.getWriter();
      pw.println(airline);
      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK);
  }

  /**
   * Handles an HTTP DELETE request by removing all dictionary entries.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/plain");

      this.airline = null;

      PrintWriter pw = response.getWriter();
      pw.println(Messages.allAirlineInfoDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = Messages.missingRequiredParameter(parameterName);
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Writes all of the flight entries to the HTTP response.
   *
   * The text of the message is formatted with {@link TextDumper}
   */
  private void writeAllFlightInfo(HttpServletResponse response ) throws IOException
  {
      PrintWriter pw = response.getWriter();
      TextDumper dumper = new TextDumper(pw);
      dumper.dump(airline);

      response.setStatus( HttpServletResponse.SC_OK );
  }

  private void writeMessage(HttpServletResponse response, String message) throws IOException {
      PrintWriter pw = response.getWriter();
      pw.println(message);

      response.setStatus( HttpServletResponse.SC_OK );
  }
  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

  @VisibleForTesting
  String getFlight(String flight) {
      var flights  = this.airline.getFlights();
      for(var f: flights){
          if(flight.equals(f)){
              return f.toString();
          }
      }
      return "";
  }

  @VisibleForTesting
  String getFlights() {
      String res = "";
      var flights  = this.airline.getFlights();
      for(var f: flights){
            res += f.toString();
            res += "\n";
      }
      return res;
  }

}
