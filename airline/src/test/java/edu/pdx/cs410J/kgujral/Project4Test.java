package edu.pdx.cs410J.kgujral;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Project4} class
 * */
public class Project4Test {
    /**a valid argument array with all options*/
    String[] validArgsAllOp = {"-print", "-textFile", "filepath", "-README", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "am", "SFO", "3/15/2023", "3:33", "pm"};
    /**a valid argument array with no options*/
    String[] validArgsNoOp = {"My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03","am" ,"SFO", "3/15/2023", "3:33", "pm"};
    String[] validArgsPrettyOpStd = {"-print", "-pretty", "-","My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03","am" ,"SFO", "3/15/2023", "3:33", "pm"};

    /**
     * the readMe() method should read README.txt
     * as a project resource and return the content
     * in form of a string
     * */
    @Test
    void readMeShouldReturnContentOfReadMeFile(){
        try{
            var content = Project4.readMe();
            assertThat(content, containsString("CS501 Project 4:: Storing Airlines as XML"));
            assertThat(content, containsString("- Kaushambi Gujral"));
        }
        catch(IOException e){
            var expected = "";
            assertThat(expected, containsString("Readme could not be read!"));
        }
    }

    /**Airline name must not be null or empty*/
    @Test
    void shouldValidateAirlineName(){
        assertFalse(Project4.isValidAirlineName(null));
        assertFalse(Project4.isValidAirlineName(""));
        assertFalse(Project4.isValidAirlineName(" "));
        assertFalse(Project4.isValidAirlineName("      "));
        assertTrue(Project4.isValidAirlineName("valid1name"));
        assertTrue(Project4.isValidAirlineName("^^^^^^^"));
    }
    /**Flight number must be a valid whole number*/
    @Test
    void shouldValidateFlightNumber(){
        boolean result = Project4.isValidFlightNumber(null);
        assertThat(result, is(false));
        result = Project4.isValidFlightNumber("1234a");
        assertThat(result, is(false));
        result = Project4.isValidFlightNumber("1234.345");
        assertThat(result, is(false));
        result = Project4.isValidFlightNumber("-1234");
        assertThat(result, is(false));
        result = Project4.isValidFlightNumber("1234");
        assertThat(result, is(true));
    }

    /**Airport code must be a valid 3-letter word*/
    @Test
    void shouldValidateAirportCode(){
        assertFalse(Project4.isValidAirportCode(null));
        assertFalse(Project4.isValidAirportCode(""));
        assertFalse(Project4.isValidAirportCode("123"));
        assertTrue(Project4.isValidAirportCode("ABC"));
    }
    /**Airport code must correspond to a valid name*/
    @Test
    void shouldValidateAirportName(){
        assertFalse(Project4.isValidAirportName(null));
        assertFalse(Project4.isValidAirportName(""));
        assertFalse(Project4.isValidAirportName("ABC"));
        assertTrue(Project4.isValidAirportName("BOM"));
    }

    /**Date must be in mm/dd/yyyy format*/
    @Test
    void shouldValidateDate(){
        assertFalse(Project4.isValidDate(null));
        assertFalse(Project4.isValidDate(""));
        assertFalse(Project4.isValidDate("12-22-2022"));
        assertFalse(Project4.isValidDate("12,22 2022"));
        assertTrue(Project4.isValidDate("12/22/2022"));
    }
    /**Time must be in hh:mm format*/
    @Test
    void shouldValidateTime(){
        assertTrue(Project4.isValidTime("12:00"));
        assertFalse(Project4.isValidTime("22:00"));
        assertFalse(Project4.isValidTime(""));
        assertFalse(Project4.isValidTime(null));
        assertFalse(Project4.isValidTime("22:600"));
        assertFalse(Project4.isValidTime("25:00"));
        assertFalse(Project4.isValidTime("16.00"));
    }

    /** Time must have the 12-hour indicator: am/pm */
    @Test
    void shouldValidateTimeMarker(){
        assertFalse(Project4.isValidTimeMarker("ao"));
        assertTrue(Project4.isValidTimeMarker("am"));
        assertTrue(Project4.isValidTimeMarker("AM"));
        assertTrue(Project4.isValidTimeMarker("pm"));
    }

    @Test
    void shouldValidateFlightDuration(){
        Date oldDate = new Date(100010001000L);
        Date newDate = new Date();
        assertFalse(Project4.isValidFlightDuration(newDate, oldDate));
        assertFalse(Project4.isValidFlightDuration(newDate, newDate));
        assertTrue(Project4.isValidFlightDuration(oldDate, newDate));
    }

    /**Utility method should return the index where arguments begin from if options are provided*/
    @Test
    void getArgumentIndexWithOps(){
        int result = Project4.getArgumentIndex(validArgsAllOp);
        assertThat(result, is(not(0)));
        assertThat(result, is(4));
    }
    @Test
    void getArgumentIndexWithPrettyPrintOps(){
        int result = Project4.getArgumentIndex(validArgsPrettyOpStd);
        assertThat(result, is(3));
    }

    @Test
    void getArgumentIndexWithAllOps(){
        String[] args = {"-print", "-pretty", "-", "-textFile", "some-file", "British Airways", "234", "SFO", "3/12/2022", "11:30", "am", "SFO", "3/12/2022", "3:33", "pm"};
        int result = Project4.getArgumentIndex(args);
        assertThat(result, is(5));
    }

    /**Utility method should return the index where arguments begin from if options are not provided*/
    @Test
    void getArgumentIndexWithoutOps(){
        int result = Project4.getArgumentIndex(validArgsNoOp);
        assertThat(result, is(0));
    }

    /**Options should be parsed and added to a hashmap*/
    @Test
    void shouldParseOptions(){
        var argMap = Project4.parseOptions(validArgsAllOp);
        assertThat(argMap.get("readme"), is(not(nullValue())));
        assertThat(argMap.get("print"), is(not(nullValue())));
        assertThat(argMap.get("textFile"), is(not(nullValue())));
        assertThat(argMap.get("textFile"), equalTo("filepath.txt"));

        argMap = Project4.parseOptions(validArgsNoOp);
        assertThat(argMap.get("readme"), is(nullValue()));
        assertThat(argMap.get("print"), is(nullValue()));
        assertThat(argMap.get("textFile"), is(nullValue()));
    }

    /** When pretty option is passed, the location must be stored too.*/
    @Test
    void shouldParseOptionsWithPretty(){
        var argMap = Project4.parseOptions(validArgsPrettyOpStd);
        assertThat(argMap.get("pretty"), equalTo("-"));
        validArgsPrettyOpStd[2] = "myTextFile";
        argMap = Project4.parseOptions(validArgsPrettyOpStd);
        assertThat(argMap.get("pretty"), equalTo("myTextFile.txt"));
    }

    /**.txt file extension should be appended to the filename if it is not present*/
    @Test
    void shouldAddFileExtensionIfNotProvided(){
        String[] args = new String[] {"-textFile", "fileName", "PDX"};
        var opMap = Project4.parseOptions(args);
        assertThat(opMap.get("textFile"), equalTo("fileName.txt"));

        args[1] = "somefile.txt";
        opMap = Project4.parseOptions(args);
        assertThat(opMap.get("textFile"), equalTo("somefile.txt"));

    }

    /** Should catch exception if pretty file cannot be accessed */
    @Test
    void shouldIssueErrorIfPrettyFileCannotBeAccessed(){
        Airline airline = new Airline("Sample Airline");
        Project4.prettyPrint(airline, "");
    }

    /**Arguments passed by the user must be parsed and added onto a hashmap*/
    @Test
    void shouldParseArgs(){
        var argMap = Project4.parseArgs(validArgsNoOp);
        assertThat(argMap.get("airline"), equalTo("My Awesome Airways"));
        assertThat(argMap.get("flightNumber"), equalTo("1234"));
        assertThat(argMap.get("src"), equalTo("PDX"));
        assertThat(argMap.get("departDate"), equalTo("3/15/2023"));
        assertThat(argMap.get("departTime"), equalTo("1:03 am"));
        assertThat(argMap.get("dest"), equalTo("SFO"));
        assertThat(argMap.get("arriveDate"), equalTo("3/15/2023"));
        assertThat(argMap.get("arriveTime"), equalTo("3:33 pm"));
    }

    /**The missing argument should be detected in the user input*/
    @Test
    void shouldDetectIfOneArgIsMissing(){
        var argMap = Project4.parseArgs(new String[]{"AirlineName", "1234", "PDX", "3/15/2023", "am", "SFO", "3/15/2023", "3:33", "pm"});
        assertNull(argMap);
    }
    /**The missing arguments should be detected in the user input*/
    @Test
    void shouldDetectIfTwoArgsAreMissing(){
        var argMap = Project4.parseArgs(new String[]{"AirlineName", "1234", "PDX", "3/15/2023", "3/15/2023", "3:33", "pm"});
        assertNull(argMap);
    }
    /**The missing arguments should be detected in the user input and the hashmap should not contain those values*/
    @Test
    void shouldDetectIfArgsAreMissing(){
        var argMap = Project4.parseArgs(new String[]{"1234", "PDX", "3/15/2023", "3:33", "am", "SFO", "3/15/2023", "3:33", "pm"});
        assertNull(argMap);
        argMap = Project4.parseArgs(new String[]{"Airline", "PDX", "3/15/2023", "3:33", "am" ,"SFO", "3/15/2023", "3:33", "pm"});
        assertNull(argMap);
        argMap = Project4.parseArgs(new String[]{"Airline", "1234", "3/15/2023", "3:33", "am","SFO", "3/15/2023", "3:33", "pm"});
        assertNull(argMap);
        argMap = Project4.parseArgs(new String[]{"Airline", "1234", "PDX", "3:33", "am", "SFO", "3/15/2023", "3:33", "pm"});
        assertNull(argMap);
        argMap = Project4.parseArgs(new String[]{"Airline", "1234", "PDX", "3/15/2023", "SFO", "3/15/2023", "3:33", "pm"});
        assertNull(argMap);
        argMap = Project4.parseArgs(new String[]{"Airline", "1234", "PDX", "3/15/2023", "3:33", "am", "3/15/2023", "3:33", "pm"});
        assertNull(argMap);
        argMap = Project4.parseArgs(new String[]{"Airline", "1234", "PDX", "3/15/2023", "3:33", "am", "SFO", "3:33", "pm"});
        assertNull(argMap);
        argMap = Project4.parseArgs(new String[]{"Airline", "1234", "PDX", "3/15/2023", "3:33", "am", "SFO", "3/15/2023", "3:33"});
        assertNull(argMap);
        argMap = Project4.parseArgs(new String[]{"Airline", "1234", "PDX", "3/15/2023", "3:33", "SFO", "3/15/2023", "3:33", "pm"});
        assertNull(argMap);
    }
}
