package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for {@link XmlDumper} class */
public class XmlDumperTest {
    /** Should be able to dump contents
     * onto an XML file such that the file is
     * parsable by the XmlParser*/
    @Test
    void shouldDumpElementsIntoXmlFile(@TempDir File tempDir) throws IOException, ParserException {
        Airline airline = new Airline("Valid Airline");
        airline.addFlight(new Flight(1234, "SFO", "PDX", DateHelper.stringToDate("3/3/2023 11:00 pm"), DateHelper.stringToDate("3/4/2023 2:00 am")));
        airline.addFlight(new Flight(1356, "BOM", "PDX", DateHelper.stringToDate("3/3/2023 5:00 pm"), DateHelper.stringToDate("3/5/2023 7:20 am")));
        File xmlFile = new File(tempDir, "airline.xml");
        XmlDumper dumper = new XmlDumper(xmlFile);
        dumper.dump(airline);

        XmlParser parser = new XmlParser(xmlFile);
        var parsedAirline = parser.parse();
        assertNotNull(parsedAirline);
        assertThat(parsedAirline.toString(), containsString("Valid Airline with 2 flights"));
        assertThat(parsedAirline.getFlights().size(), equalTo(2));
        assertThat(parsedAirline.getFlights().toArray()[0].toString(), equalTo("Flight 1234 departs SFO at 3/3/23, 11:00 PM arrives PDX at 3/4/23, 2:00 AM"));
        assertThat(parsedAirline.getFlights().toArray()[1].toString(), equalTo("Flight 1356 departs BOM at 3/3/23, 5:00 PM arrives PDX at 3/5/23, 7:20 AM"));
    }

    @Test
    void shouldIssueError(@TempDir File tempDir) throws IOException {
        File xmlFile = new File(tempDir, "airline");
        XmlDumper dumper = new XmlDumper(xmlFile);
        dumper.dump(null);

        XmlParser parser = new XmlParser(xmlFile);
        var thrown = assertThrows(ParserException.class, parser::parse);
        assertEquals("There was a problem parsing the xml file :-(", thrown.getMessage());
    }
}
