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
}
