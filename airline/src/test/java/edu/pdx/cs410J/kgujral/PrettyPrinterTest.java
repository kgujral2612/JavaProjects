package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link PrettyPrinter} class
 * */
public class PrettyPrinterTest {
    @Test
    void airlineAndFlightInfoIsDumped(@TempDir File tempDir) throws IOException, ParserException {
        String airlineName = "Indigo Airlines";
        Airline airline = new Airline(airlineName);
        Flight f1 = new Flight(1, "CHA", "SFO", DateHelper.stringToDate("3/3/2020 05:40 pm"), DateHelper.stringToDate("3/3/2020 06:40 pm"));
        Flight f2 = new Flight(2, "BNA", "SFO", DateHelper.stringToDate("3/3/2020 05:40 am"), DateHelper.stringToDate("3/3/2020 09:40 am"));
        airline.addFlight(f1);
        airline.addFlight(f2);
        File textFile = new File(tempDir, "airline.txt");
        PrettyPrinter dumper = new PrettyPrinter(new FileWriter(textFile));
        dumper.dump(airline);

        TextParser parser = new TextParser(new FileReader(textFile));
        String content = parser.readText();
        assertNotNull(content);
        assertThat(content, containsString(airlineName));
        assertThat(content, containsString("Flight # 2\n" +
                "From Nashville, TN To San Francisco, CA\n" +
                "Departs at: Mar 3, 2020, 5:40:00 AM\n" +
                "Arrives at: Mar 3, 2020, 9:40:00 AM\n" +
                "Total Duration of Flight: 240 minutes."));
        assertThat(content, containsString("Flight # 1\n" +
                "From Chattanooga, TN To San Francisco, CA\n" +
                "Departs at: Mar 3, 2020, 5:40:00 PM\n" +
                "Arrives at: Mar 3, 2020, 6:40:00 PM\n" +
                "Total Duration of Flight: 60 minutes."));
    }
}
