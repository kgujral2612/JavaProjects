package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.net.URL;
import java.util.Date;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for {@link XmlParser class} */
public class XmlParserTest {

    /** When the xml file is valid, it should be parsed successfully*/
    @Test
    void shouldParseValidXml() throws ParserException {
        var fileName = "valid-airline.xml";
        URL url = this.getClass().getResource(fileName);
        assertNotNull(url);
        File file = new File(url.getFile());
        XmlParser parser = new XmlParser(file);

        Airline airline = parser.parse();
        assertThat(airline.toString(), containsString("Valid Airlines with 2 flights"));
        assertThat(airline.getFlights().size(), equalTo(2));
        assertThat(airline.getFlights().toArray()[0].toString(),
                equalTo("Flight 1437 departs BJX at 9/25/20, 5:00 PM arrives CMN at 9/26/20, 3:56 AM"));
        assertThat(airline.getFlights().toArray()[1].toString(),
                equalTo("Flight 7865 departs JNB at 5/15/20, 7:24 AM arrives XIY at 5/16/20, 9:07 AM"));
    }
    /** When the xml file is invalid,
     * it should not be parsed successfully
     * and ParserException should be thrown */
    @Test
    void shouldNotParseInValidXml() {
        var fileName = "invalid-airline.xml";
        URL url = this.getClass().getResource(fileName);
        assertNotNull(url);
        File file = new File(url.getFile());
        XmlParser parser = new XmlParser(file);
        assertThrows(ParserException.class, parser::parse);
    }

    /** When the xml file has no flights
     */
    @Test
    void shouldReturnNullForXmlWithNoAirline() throws ParserException {
        var fileName = "airline-no-flights.xml";
        URL url = this.getClass().getResource(fileName);
        assertNotNull(url);
        File file = new File(url.getFile());
        XmlParser parser = new XmlParser(file);
        assertNotNull(parser.parse());
    }

    /** When the xml file does not have name,
     * it should throw error */
    @Test
    void shouldThrowErrorIfAirlineNameIsMissing(){
        var fileName = "missing-airline-name.xml";
        URL url = this.getClass().getResource(fileName);
        assertNotNull(url);
        File file = new File(url.getFile());
        XmlParser parser = new XmlParser(file);
        var thrown = assertThrows(ParserException.class, parser::parse);
        assertEquals("The XML file does not conform to the DTD.", thrown.getMessage());
    }

    /** When the xml file only contains the airline
     * and no flights, the file must be parsed
     * successfully
     */
    @Test
    void shouldParseFileWithAirlineButNoFlights(@TempDir File tempDir) throws IOException, ParserException {
        File file = new File(tempDir, "tempFile.xml");
        Airline airline = new Airline("Temp Airline");
        XmlDumper dumper = new XmlDumper(file);
        dumper.dump(airline);

        XmlParser parser = new XmlParser(file);
        Airline parsedAirline = parser.parse();
        assertNotNull(parsedAirline);
    }

    /** When the xml file contains
     * invalid flight src
     */
    @Test
    void shouldIssueErrorIfFlightSrcIsInvalid(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "tempFile.xml");
        Airline airline = new Airline("Temp Airline");
        airline.addFlight(new Flight(123, "aabbcc", "SFO", new Date(), new Date()));
        XmlDumper dumper = new XmlDumper(file);
        dumper.dump(airline);

        XmlParser parser = new XmlParser(file);
        assertThrows(ParserException.class, parser::parse);
        var thrown = assertThrows(ParserException.class, parser::parse);
        assertEquals("Flight Source was missing or invalid. Was aabbcc | Expected a 3-letter code like PDX", thrown.getMessage());
    }

    /** When the xml file contains
     * invalid flight dest
     */
    @Test
    void shouldIssueErrorIfFlightDestIsInvalid(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "tempFile.xml");
        Airline airline = new Airline("Temp Airline");
        airline.addFlight(new Flight(123, "SFO", "XXX", new Date(), new Date()));
        XmlDumper dumper = new XmlDumper(file);
        dumper.dump(airline);

        XmlParser parser = new XmlParser(file);
        var thrown = assertThrows(ParserException.class, parser::parse);
        assertEquals("Flight Destination was missing or invalid. Was XXX | Expected a 3-letter code like SFO", thrown.getMessage());
    }

    /** When the file does not exist,
     * a ParserException should be thrown */
    @Test
    void shouldReturnNullAirlineIfFileDoesNotExist(@TempDir File tempDir) throws ParserException {
        File file = new File(tempDir, "file-not-yet-created.xml");
        XmlParser parser = new XmlParser(file);
        var thrown = assertThrows(RuntimeException.class, parser::parse);
        assertEquals("File was not found.", thrown.getMessage());
    }
}
