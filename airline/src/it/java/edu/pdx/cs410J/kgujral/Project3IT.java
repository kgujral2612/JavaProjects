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
 * An integration test for the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {
    /** valid list of arguments with readme option */
    String[] validArgsReadmeOp = {"-README", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "am", "SFO", "3/15/2023", "3:33", "pm"};
    /** valid list of arguments with print option */
    String[] validArgsPrintOp = {"-print", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "am", "SFO", "3/15/2023", "3:33", "pm"};
    /** valid list of arguments with file option */
    String[] validArgsPrintFileOp = {"-print", "-textFile", "FilePath", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "am" , "SFO", "3/15/2023", "3:33", "pm"};
    /** valid list with readme option and not arguments */
    String[] readmeOp = {"-README"};
    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) { return invokeMain( Project3.class, args ); }

    /** When no arguments are provided, usage should be displayed for the user */
    @Test
    void shouldDisplayUsageWhenNoArgumentsAreProvided(){
        var result = invokeMain();
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingCLIArguments));
        assertThat(result.getTextWrittenToStandardOut(), containsString("java -jar target/airline-2023.0.0.jar [options] <args>"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("args are (in this order):"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("airline\tThe name of the airline"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("flightNumber\tThe flight number"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("src\tThree-letter code of departure airport"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("depart\tDeparture date and time (am/pm)"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("dest\tThree-letter code of arrival airport"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("arrive\tArrival date and time (am/pm)"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("options are (options may appear in any order):"));
    }
    /** When the readme option is selected, the project README.txt
     *  should be displayed and the program should exit. */
   @Test
   void shouldDisplayReadMeWhenOptionIsSelected(){
       var result = invokeMain(validArgsReadmeOp);
       assertThat(result.getTextWrittenToStandardOut(), containsString("CS501 Project3:: Pretty Printing Your Airline"));
       result = invokeMain(readmeOp);
       assertThat(result.getTextWrittenToStandardOut(), containsString("CS501 Project3:: Pretty Printing Your Airline"));
   }
    /** When print option is selected, the new flight information
     *  should be displayed */
    @Test
    void shouldPrintFlightInfoWhenOptionIsSelected(){
        var result = invokeMain(validArgsPrintOp);
        assertThat(result.getTextWrittenToStandardOut(), equalTo("Flight 1234 departs PDX at 3/15/2023 1:03 am arrives SFO at 3/15/2023 3:33 pm\n"));
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
        String [] args = new String[] {"-textFile", "/Users/kaushambigujral/Desktop/git/PSUWinter23KG/airline/src/test/resources/edu/pdx/cs410J/kgujral/airline-flight-info.txt", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "am", "SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.airlineNameMismatch, "My Awesome Airways", "Alaska Airlines")));
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
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.ioError, args[1])));
    }
    /** When the airline name is invalid
     * the program should issue
     * an error message for the user*/
   @Test
    void shouldValidateAirlineName(){
       String[] args = {"", "1234", "PDX", "3/15/2023", "1:03", "am", "SFO", "3/15/2023", "3:33", "pm"};
       var result = invokeMain(args);
       assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Airline Name", args[0], "A non-empty String. eg: British Airways")));
   }
    /** When the flight number is invalid
     * the program should issue
     * an error message for the user*/
   @Test
    void shouldValidateFlightNumber(){
       String[] args = {"British Airways", "1a23", "PDX", "3/15/2023", "1:03", "am", "SFO", "3/15/2023", "3:33", "pm"};
       var result = invokeMain(args);
       assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Flight Number", args[1], "A whole number. eg: 6478")));
   }
    /** When the departure airport code is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateDepartureAirportCode(){
        String[] args = {"British Airways", "123", "P1X", "3/15/2023", "1:03", "am", "SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Departure Airport Code", args[2], "A 3-letter String. eg: PDX")));
    }
    /** When the departure date is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateDepartureDate(){
        String[] args = {"British Airways", "123", "PDX", "3-15-2023", "1:03", "am", "SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Date of Departure", args[3], "A date in the format mm/dd/yyyy. eg: 11/03/1997")));
    }
    /** When the departure time is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateDepartureTime(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "103", "am", "SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Time of Departure", args[4], "12-hour time in the format hh:mm. eg: 05:45")));
    }
    /** When the departure time is not succeeded by am/pm
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateDepartureTimeIndication(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "1:03", "po", "SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Time of Departure", args[5], "time must be succeeded by am/pm")));
    }

    /** When the arrival airport code is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateArrivalAirportCode(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "1:03", "am", "S4O", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Arrival Airport Code", args[6], "A 3-letter String. eg: SFO")));
    }
    /** When the arrival date is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateArrivalDate(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "1:03", "am", "SFO", "3-15-2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Date of Arrival", args[7], "A date in the format mm/dd/yyyy. eg: 11/03/1997")));
    }
    /** When the arrival time is invalid
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateArrivalTime(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "1:03", "am", "SFO", "3/15/2023", "333", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Time of Arrival", args[8], "12-hour time in the format hh:mm. eg: 08:05")));
    }
    /** When the departure time is not succeeded by am/pm
     * the program should issue
     * an error message for the user*/
    @Test
    void shouldValidateArrivalTimeIndication(){
        String[] args = {"British Airways", "123", "PDX", "3/15/2023", "1:03", "AM", "SFO", "3/15/2023", "3:33", "bogus meridian"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(String.format(Project3.invalidArgument, "Time of Arrival", args[9], "time must be succeeded by am/pm")));
    }
    /** When the Flight Number is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingFlightNumber(){
        String[] args = {"British Airways", "PDX", "3/15/2023", "1:03","am", "SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Flight Number"));
    }
    /** When the Departure Airport Code is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingSource(){
        String[] args = {"British Airways", "234", "3/15/2023", "1:03", "am","SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Departure Airport Code"));
    }
    /** When the Date of Departure is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingDepartureDate(){
        String[] args = {"British Airways", "234", "SFO", "1:03", "am", "SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Date of Departure"));
    }
    /** When the Time of Departure is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingDepartureTime(){
        String[] args = {"British Airways", "234", "SFO", "3/15/2023", "am", "SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Time of Departure"));
    }
    /** When am/pm is missing after the Time of Departure
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingDepartureTimeIndication(){
        String[] args = {"British Airways", "234", "SFO", "3/3/2023", "SFO", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("am/pm after time of departure"));
    }
    /** When the Arrival Airport Code is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingDest(){
        String[] args = {"British Airways", "234", "PDX","3/15/2023", "1:03", "am", "3/15/2023", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Arrival Airport Code"));
    }
    /** When the Date of Arrival is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingArriveDate(){
        String[] args = {"British Airways", "234", "PDX", "1:03", "am", "SFO", "3:33", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Date of Arrival"));
    }
    /** When the Time of Arrival is missing
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingArrivalTime(){
        String[] args = {"British Airways", "234", "SFO", "3/15/2023", "1:30", "am","SFO", "3/15/2023", "pm"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("Time of Arrival"));
    }
    /** When am/pm is missing after the Time of Arrival
     * the program should report it to the user*/
    @Test
    void shouldIdentifyMissingArrivalTimeIndication(){
        String[] args = {"British Airways", "234", "SFO", "3/3/2023", "1:30", "am", "SFO", "3/15/2023", "3:33"};
        var result = invokeMain(args);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.missingArguments));
        assertThat(result.getTextWrittenToStandardError(), containsString("am/pm after time of arrival"));
    }
}