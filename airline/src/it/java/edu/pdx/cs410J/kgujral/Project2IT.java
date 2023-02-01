package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * An integration test for the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {
    /** valid list of arguments with readme option */
    String[] validArgsReadmeOp = {"-README", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    /** valid list of arguments with print option */
    String[] validArgsPrintOp = {"-print", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    /** valid list of arguments with file option */
    String[] validArgsPrintFileOp = {"-print", "-textFile", "FilePath", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    /** valid list with readme option and not arguments */
    String[] readmeOp = {"-README"};
    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) { return invokeMain( Project2.class, args ); }

    /** When no arguments are provided, usage should be displayed for the user */
    @Test
    void shouldDisplayUsageWhenNoArgumentsAreProvided(){
        var result = invokeMain();
        assertThat(result.getTextWrittenToStandardError(), containsString(Project2.missingCLIArguments));
        assertThat(result.getTextWrittenToStandardOut(), containsString("java -jar target/airline-2023.0.0.jar [options] <args>"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("args are (in this order):"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("airline\tThe name of the airline"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("flightNumber\tThe flight number"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("src\tThree-letter code of departure airport"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("depart\tDeparture date and time (24-hour time)"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("dest\tThree-letter code of arrival airport"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("arrive\tArrival date and time (24-hour time)"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("options are (options may appear in any order):"));
    }
    /** When the readme option is selected, the project README.txt
     *  should be displayed and the program should exit. */
   @Test
   void shouldDisplayReadMeWhenOptionIsSelected(){
       var result = invokeMain(validArgsReadmeOp);
       assertThat(result.getTextWrittenToStandardOut(), containsString("CS501 Project2: : Storing An Airline in a Text File"));
       result = invokeMain(readmeOp);
       assertThat(result.getTextWrittenToStandardOut(), containsString("CS501 Project2: : Storing An Airline in a Text File"));
   }
    /** When print option is selected, the new flight information
     *  should be displayed */
    @Test
    void shouldPrintFlightInfoWhenOptionIsSelected(){
        var result = invokeMain(validArgsPrintOp);
        assertThat(result.getTextWrittenToStandardOut(), equalTo("Flight 1234 departs PDX at 3/15/2023 1:03 arrives SFO at 3/15/2023 3:33\n"));
    }
    /** When file is not present, a new file should be created
     * in the directory and airline information should be added onto it */
    @Test
    void shouldAddFlightDetailsToNewFileWhenFileNotPresentWithOp(@TempDir File tempDir) throws FileNotFoundException, ParserException {
        var filePath = tempDir + "/airline.txt";
        validArgsPrintFileOp[2] = filePath;
        invokeMain(validArgsPrintFileOp);

        TextParser parser = new TextParser(new FileReader(filePath));
        Airline read = parser.parse();
        assertThat(read.getName(), equalTo(validArgsPrintFileOp[3]));
    }
    /** When the airline name in the text file is different from
     * the airline in the arguments, the program should issue
     * an error message for the user*/
    @Disabled("Testing absolute path")
    @Test
    void shouldNotAddFlightDetailsIfAirlineNameIsDifferentWithOp(){
        String [] args = new String[] {"-textFile", "/Users/kaushambigujral/Desktop/git/PSUWinter23KG/airline/src/test/resources/edu/pdx/cs410J/kgujral/airline-flight-info.txt", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.airlineNameMismatch, "My Awesome Airways", "Alaska Airlines")));
    }
    /** When the text file contains invalid data items,
     * the program should identify and should issue
     * an error message for the user*/
    @Disabled("Testing absolute path")
    @Test
    void shouldNotAddFlightDetailsIfFileContainsErrorsWithOp(){
        String[] args = new String[]{"-textFile", "/Users/kaushambigujral/Desktop/git/PSUWinter23KG/airline/src/test/resources/edu/pdx/cs410J/kgujral/invalid-airline-flight-info.txt", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Departure Airport Code in text file! Was PDX 28/01/2023 19:00 | Expected A 3-letter String. eg: PDX"));
    }
    /** When the file path is incorrect (no such directory),
     * an error message for the user should be displayed*/
    @Test
    void shouldIssueErrorIfFilePathDoesNotExist(){
        String[] args = new String[]{"-textFile", "dir/some.txt", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.ioError, args[1])));
    }
    /** When the airline name is invalid
     * the program should issue
     * an error message for the user*/
   @Test
    void shouldValidateAirlineName(){
       String[] args = {"", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
       var result = invokeMain(args);
       assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.invalidArgument, "Airline Name", args[0], "A non-empty String. eg: British Airways")));
   }
    /** When the flight number is invalid
     * the program should issue
     * an error message for the user*/
   @Test
    void shouldValidateFlightNumber(){
       String[] args = {"British Airways", "1a23", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
       var result = invokeMain(args);
       assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.invalidArgument, "Flight Number", args[1], "A whole number. eg: 6478")));
   }
    /** When the departure airport code is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateDepartureAirportCode(){
        String[] args = {"British Airways", "123", "P1X", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.invalidArgument, "Departure Airport Code", args[2], "A 3-letter String. eg: PDX")));
    }
    /** When the departure date is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateDepartureDate(){
        String[] args = {"British Airways", "123", "PDX", "3-15-2023", "1:03", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.invalidArgument, "Date of Departure", args[3], "A date in the format mm/dd/yyyy. eg: 11/03/1997")));
    }
    /** When the departure time is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateDepartureTime(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "103", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.invalidArgument, "Time of Departure", args[4], "24-hour time in the format hh:mm. eg: 05:45")));
    }
    /** When the arrival airport code is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateArrivalAirportCode(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "1:03", "S4O", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.invalidArgument, "Arrival Airport Code", args[5], "A 3-letter String. eg: SFO")));
    }
    /** When the arrival date is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateArrivalDate(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "1:03", "SFO", "3-15-2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.invalidArgument, "Date of Arrival", args[6], "A date in the format mm/dd/yyyy. eg: 11/03/1997")));
    }
    /** When the arrival time is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateArrivalTime(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "333"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project2.invalidArgument, "Time of Arrival", args[7], "24-hour time in the format hh:mm. eg: 08:05")));
    }
    /** When the Flight Number is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingFlightNumber(){
        String[] args = {"British Airways", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project2.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Flight Number"));
    }
    /** When the Departure Airport Code is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingSource(){
        String[] args = {"British Airways", "234", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project2.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Departure Airport Code"));
    }
    /** When the Date of Departure is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingDepartureDate(){
        String[] args = {"British Airways", "234", "SFO", "1:03", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project2.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Date of Departure"));
    }
    /** When the Time of Departure is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingDepartureTime(){
        String[] args = {"British Airways", "234", "SFO", "3/15/2023", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project2.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Time of Departure"));
    }
    /** When the Arrival Airport Code is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingDest(){
        String[] args = {"British Airways", "234", "PDX","3/15/2023", "1:03", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project2.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Arrival Airport Code"));
    }
    /** When the Date of Arrival is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingArriveDate(){
        String[] args = {"British Airways", "234", "PDX", "1:03", "SFO", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project2.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Date of Arrival"));
    }
    /** When the Time of Arrival is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingArrivalTime(){
        String[] args = {"British Airways", "234", "SFO", "3/15/2023", "1:30", "SFO", "3/15/2023"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project2.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Time of Arrival"));
    }
}