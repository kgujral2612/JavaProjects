package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/** Unit tests for {@link Converter}*/
public class ConverterIT extends InvokeMainTestCase {
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Converter.class, args ); }

    /**When no command line arguments are passed
     * usage should be displayed*/
    @Test
    void shouldPrintUsageIfNoArgsAreProvided(){
        String[] args = new String[]{};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), equalTo(Converter.missingCLIArguments + "\n"));
    }

    /** When command line arguments passed are not 2,
     *  an error message must be issued*/
    @Test
    void shouldDisplayErrorMsgWhenArgsAreMoreOrLess(){
        String[] args = {"sometxtfile"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), equalTo("Too few args. Was 1 args | Expected 2 arguments, i.e textFilePath and xmlFilePath\n"));

        String[] args2 = {"sometxtfile", "someotherfile", "somexmlfile"};
        result = invokeMain(args2);
        assertThat(result.getTextWrittenToStandardError(), equalTo("Too many args. Was 3 args | Expected 2 arguments, i.e textFilePath and xmlFilePath\n"));
    }

    /** When the text file location is invalid,
     * an error message must be issued*/
    @Test
    void shouldDisplayErrorMsgWhenFileLocationIsInvalid(@TempDir File tempDir){
        File txtFile = new File(tempDir, "txtFile.txt");
        File xmlFile = new File(tempDir, "xmlFile.xml");
        String[] args = {txtFile.getPath(), xmlFile.getPath()};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString("The file location given by you"));
        assertThat(result.getTextWrittenToStandardError(), containsString("cannot be found."));
    }

    /** When txt file contains the airline but no flights,
     * should dump airline into xml*/
    @Test
    void shouldDumpAirlineWithNoFlightsIntoXml(@TempDir File tempDir) throws IOException, ParserException {
        File txtFile = new File(tempDir, "txtFile.txt");
        File xmlFile = new File(tempDir, "xmlFile.xml");
        String[] args = {txtFile.getParent() + "/txtFile", xmlFile.getParent() + "/xmlFile"};

        Airline airline = new Airline("Airline For Conversion");
        TextDumper dumper = new TextDumper(new FileWriter(txtFile));
        dumper.dump(airline);

        invokeMain(args);

        XmlParser parser = new XmlParser(xmlFile);
        var airlineFromXml = parser.parse();
        assertThat(airlineFromXml.toString(), equalTo("Airline For Conversion with 0 flights"));
    }

    /** When all inputs are valid,
     * the program should successfully dump contents into the xml file */
    @Test
    void shouldDumpIntoXml(@TempDir File tempDir) throws IOException, ParserException {
        File txtFile = new File(tempDir, "txtFile.txt");
        File xmlFile = new File(tempDir, "xmlFile.xml");
        String[] args = {txtFile.getPath(), xmlFile.getPath()};

        Airline airline = new Airline("Airline For Conversion");
        airline.addFlight(new Flight(7302, "BOM", "PDX", DateHelper.stringToDate("3/3/2023 5:00 pm"), DateHelper.stringToDate("3/5/2023 7:20 am")));
        airline.addFlight(new Flight(8401, "SFO", "PDX", DateHelper.stringToDate("3/3/2023 11:00 pm"), DateHelper.stringToDate("3/4/2023 2:00 am")));

        TextDumper dumper = new TextDumper(new FileWriter(txtFile));
        dumper.dump(airline);

        invokeMain(args);

        XmlParser parser = new XmlParser(xmlFile);
        var airlineFromXml = parser.parse();
        assertThat(airlineFromXml.toString(), equalTo("Airline For Conversion with 2 flights"));
        assertThat(airlineFromXml.getFlights().toArray()[0].toString(), equalTo("Flight 7302 departs BOM at 3/3/23, 5:00 PM arrives PDX at 3/5/23, 7:20 AM"));
    }
}
