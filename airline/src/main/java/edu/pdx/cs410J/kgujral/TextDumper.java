package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AirlineDumper;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * A skeletal implementation of the <code>TextDumper</code> class for Project 2.
 */
public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;

  /** Parameterized constructor that assigns the value of the given writer to the writer data member
   * @param writer the writer object*/
  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * The method that writes the contents of an airline (and its flights) onto a text file.
   * @param airline the airline object that needs to be written inside the text file
   * */
  @Override
  public void dump(Airline airline) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
      ) {
      pw.println(airline.getName());
      for(var flight: airline.getFlights()){
        pw.println(flight.getNumber());
        pw.println(flight.getSource());
        pw.println(flight.getDepartureString().replace(",", ""));
        pw.println(flight.getDestination());
        pw.println(flight.getArrivalString().replace(",", ""));
      }
      pw.flush();
    }
  }
}
