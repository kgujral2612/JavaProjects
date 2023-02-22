package edu.pdx.cs410J.kgujral;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertNull;

/** Unit tests for {@link Converter}*/
public class ConverterTest {

    /** When the text file location is invalid,
     * the airline should be null*/
    @Test
    void shouldReturnNullAirlineWhenFileLocationIsInvalid(@TempDir File tempDir){
        File txtFile = new File(tempDir, "txtFile.txt");
        var result = Converter.readAirline(txtFile.getPath());
        assertNull(result);
    }

    /** When the airline returned by the text file is null,
     * it should not be dumped into a xml file*/
    @Test
    void shouldDisplayErrorWhenNullAirlineIsDumpedIntoXml(@TempDir File tempDir) throws ParserException {
        File xmlFile = new File(tempDir, "xmlFile.xml");
        Converter.dumpAirlineToXml(null, xmlFile.getPath());

        XmlParser parser = new XmlParser(xmlFile);
        var airlineFromXml = parser.parse();
        assertNull(airlineFromXml);
    }
}
