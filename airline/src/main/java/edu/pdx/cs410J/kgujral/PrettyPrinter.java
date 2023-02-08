package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AirlineDumper;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * The Flight class which implements the {@link AirlineDumper} interface
 * */
public class PrettyPrinter implements AirlineDumper<Airline>{
    private final Writer writer;

    /** Parameterized constructor that assigns the value of the given writer to the writer data member
     * @param writer the writer object*/
    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    /**
     * The method that writes the contents of an airline (and its flights) onto a text file in a pretty format.
     * @param airline the airline object that needs to be written inside the text file
     * */
    @Override
    public void dump(Airline airline) {
        try (
                PrintWriter pw = new PrintWriter(this.writer)
        ) {
            pw.println(PrettyHelper.prettify(airline));
            pw.flush();
        }
    }
}
