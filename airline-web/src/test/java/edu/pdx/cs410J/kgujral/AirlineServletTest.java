package edu.pdx.cs410J.kgujral;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {

  @Test
  void getAirline() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    assertThat(stringWriter.toString(), equalTo(""));
  }
  @Test
  void addFlightInfo() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airline = "TEST AIRLINE";
    String flightNum = "68445";
    String src = "PDX";
    String dest = "SFO";
    String depart = "03/03/2020 05:00 pm";
    String arrive = "03/03/2020 07:30 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME)).thenReturn(airline);
    when(request.getParameter(AirlineServlet.FLIGHT_NUM)).thenReturn(flightNum);
    when(request.getParameter(AirlineServlet.SRC)).thenReturn(src);
    when(request.getParameter(AirlineServlet.DEST)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.DEPART)).thenReturn(depart);
    when(request.getParameter(AirlineServlet.ARRIVE)).thenReturn(arrive);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    assertThat(stringWriter.toString(), containsString("TEST AIRLINE"));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    assertThat(servlet.getFlights(), containsString("Flight 68445 departs PDX at 3/3/20, 5:00 PM arrives SFO at 3/3/20, 7:30 PM"));
  }

  @Test
  void initiallyServletContainsNoAirlineEntries() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(pw, never()).println(anyString());
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }

}
