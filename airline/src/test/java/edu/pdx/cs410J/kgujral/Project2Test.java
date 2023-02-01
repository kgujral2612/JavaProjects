package edu.pdx.cs410J.kgujral;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Project2Test {
    String[] validArgsAllOp = {"-print", "-textFile", "filepath", "-README", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    String[] validArgsNoOp = {"My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};

    /**
     * the readMe() method should read README.txt
     * as a project resource and return the content
     * in form of a string
     * */
    @Test
    void readMeShouldReturnContentOfReadMeFile(){
        try{
            var content = Project2.readMe();
            assertThat(content, containsString("CS501 Project2: : Storing An Airline in a Text File"));
            assertThat(content, containsString("- Kaushambi Gujral"));
        }
        catch(IOException e){
            var expected = "";
            assertThat(expected, containsString("Readme could not be read!"));
        }
    }

    @Test
    void shouldValidateAirlineName(){
        boolean result = Project2.isValidAirlineName(null);
        assertThat(result, is(false));
        result = Project2.isValidAirlineName("");
        assertThat(result, is(false));
        result = Project2.isValidAirlineName(" ");
        assertThat(result, is(false));
        result = Project2.isValidAirlineName("      ");
        assertThat(result, is(false));
        result = Project2.isValidAirlineName("valid1name");
        assertThat(result, is(true));
        result = Project2.isValidAirlineName("^^^^^^^");
        assertThat(result, is(true));
    }

    @Test
    void shouldValidateFlightNumber(){
        boolean result = Project2.isValidFlightNumber(null);
        assertThat(result, is(false));
        result = Project2.isValidFlightNumber("1234a");
        assertThat(result, is(false));
        result = Project2.isValidFlightNumber("1234.345");
        assertThat(result, is(false));
        result = Project2.isValidFlightNumber("-1234");
        assertThat(result, is(false));
        result = Project2.isValidFlightNumber("1234");
        assertThat(result, is(true));
    }

    @Test
    void shouldValidateAirportCode(){
        boolean result = Project2.isValidAirportCode(null);
        assertThat(result, is(false));
        result = Project2.isValidAirportCode("");
        assertThat(result, is(false));
        result = Project2.isValidAirportCode("123");
        assertThat(result, is(false));
        result = Project2.isValidAirportCode("ABC");
        assertThat(result, is(true));
    }

    @Test
    void shouldValidateDate(){
        boolean result = Project2.isValidDate("12-22-2022");
        assertThat(result, is(false));
        result = Project2.isValidDate("12,22 2022");
        assertThat(result, is(false));
        result = Project2.isValidDate("");
        assertThat(result, is(false));
        result = Project2.isValidDate(null);
        assertThat(result, is(false));
        result = Project2.isValidDate("12/22/2022");
        assertThat(result, is(true));
    }

    @Test
    void shouldValidateTime(){
        boolean result = Project2.isValidTime("22:00");
        assertThat(result, is(true));
        result = Project2.isValidTime("22:600");
        assertThat(result, is(false));
        result = Project2.isValidTime("25:00");
        assertThat(result, is(false));
        result = Project2.isValidTime("16.00");
        assertThat(result, is(false));
        result = Project2.isValidTime("");
        assertThat(result, is(false));
        result = Project2.isValidTime("");
        assertThat(result, is(false));
        result = Project2.isValidTime(null);
        assertThat(result, is(false));
    }

    @Test
    void getArgumentIndexWithOps(){
        int result = Project2.getArgumentIndex(validArgsAllOp);
        assertThat(result, is(not(0)));
        assertThat(result, is(4));
    }

    @Test
    void getArgumentIndexWithoutOps(){
        int result = Project2.getArgumentIndex(validArgsNoOp);
        assertThat(result, is(0));
    }

    @Test
    void shouldParseOptions(){
        var argMap = Project2.parseOptions(validArgsAllOp);
        assertThat(argMap.get("readme"), is(not(nullValue())));
        assertThat(argMap.get("print"), is(not(nullValue())));
        assertThat(argMap.get("textFile"), is(not(nullValue())));
        assertThat(argMap.get("textFile"), equalTo("filepath.txt"));

        argMap = Project2.parseOptions(validArgsNoOp);
        assertThat(argMap.get("readme"), is(nullValue()));
        assertThat(argMap.get("print"), is(nullValue()));
        assertThat(argMap.get("textFile"), is(nullValue()));
    }

    @Test
    void shouldAddFileExtensionIfNotProvided(){
        String[] args = new String[] {"-textFile", "fileName", "PDX"};
        var opMap = Project2.parseOptions(args);
        assertThat(opMap.get("textFile"), equalTo("fileName.txt"));

        args[1] = "somefile.txt";
        opMap = Project2.parseOptions(args);
        assertThat(opMap.get("textFile"), equalTo("somefile.txt"));

    }

    @Test
    void shouldParseArgs(){
        var argMap = Project2.parseArgs(validArgsNoOp);
        assertThat(argMap.get("airline"), equalTo("My Awesome Airways"));
        assertThat(argMap.get("flightNumber"), equalTo("1234"));
        assertThat(argMap.get("src"), equalTo("PDX"));
        assertThat(argMap.get("departDate"), equalTo("3/15/2023"));
        assertThat(argMap.get("departTime"), equalTo("1:03"));
        assertThat(argMap.get("dest"), equalTo("SFO"));
        assertThat(argMap.get("arriveDate"), equalTo("3/15/2023"));
        assertThat(argMap.get("arriveTime"), equalTo("3:33"));
    }

    @Test
    void shouldDetectIfOneArgIsMissing(){
        var argMap = Project2.parseArgs(new String[]{"AirlineName", "1234", "PDX", "3/15/2023", "SFO", "3/15/2023", "3:33"});
        assertThat(argMap.size(), is(7));
        assertThat(argMap.get("airline"), equalTo("AirlineName"));
        assertThat(argMap.get("flightNumber"), equalTo("1234"));
        assertThat(argMap.get("src"), equalTo("PDX"));
        assertThat(argMap.get("departDate"), equalTo("3/15/2023"));
        assertThat(argMap.get("departTime"), is(nullValue()));
        assertThat(argMap.get("dest"), equalTo("SFO"));
        assertThat(argMap.get("arriveDate"), equalTo("3/15/2023"));
        assertThat(argMap.get("arriveTime"), equalTo("3:33"));
    }

    @Test
    void shouldDetectIfTwoArgsAreMissing(){
        var argMap = Project2.parseArgs(new String[]{"AirlineName", "1234", "PDX", "3/15/2023", "3/15/2023", "3:33"});
        assertThat(argMap.size(), is(6));
        assertThat(argMap.get("airline"), equalTo("AirlineName"));
        assertThat(argMap.get("flightNumber"), equalTo("1234"));
        assertThat(argMap.get("src"), equalTo("PDX"));
        assertThat(argMap.get("departDate"), equalTo("3/15/2023"));
        assertThat(argMap.get("departTime"), is(nullValue()));
        assertThat(argMap.get("dest"), is(nullValue()));
        assertThat(argMap.get("arriveDate"), equalTo("3/15/2023"));
        assertThat(argMap.get("arriveTime"), equalTo("3:33"));
    }

    @Test
    void shouldDetectIfArgsAreMissing(){
        var argMap = Project2.parseArgs(new String[]{"1234", "PDX", "3/15/2023", "3:33", "SFO", "3/15/2023", "3:33"});
        assertThat(argMap.get("flightNumber"), is(nullValue()));
        argMap = Project2.parseArgs(new String[]{"Airline", "PDX", "3/15/2023", "3:33", "SFO", "3/15/2023", "3:33"});
        assertThat(argMap.get("flightNumber"), is(nullValue()));
        argMap = Project2.parseArgs(new String[]{"Airline", "1234", "3/15/2023", "3:33", "SFO", "3/15/2023", "3:33"});
        assertThat(argMap.get("src"), is(nullValue()));
        argMap = Project2.parseArgs(new String[]{"Airline", "1234", "PDX", "3:33", "SFO", "3/15/2023", "3:33"});
        assertThat(argMap.get("departDate"), is(nullValue()));
        argMap = Project2.parseArgs(new String[]{"Airline", "1234", "PDX", "3/15/2023", "SFO", "3/15/2023", "3:33"});
        assertThat(argMap.get("departTime"), is(nullValue()));
        argMap = Project2.parseArgs(new String[]{"Airline", "1234", "PDX", "3/15/2023", "3:33", "3/15/2023", "3:33"});
        assertThat(argMap.get("dest"), is(nullValue()));
        argMap = Project2.parseArgs(new String[]{"Airline", "1234", "PDX", "3/15/2023", "3:33", "SFO", "3:33"});
        assertThat(argMap.get("arriveDate"), is(nullValue()));
        argMap = Project2.parseArgs(new String[]{"Airline", "1234", "PDX", "3/15/2023", "3:33", "SFO", "3/15/2023"});
        assertThat(argMap.get("arriveTime"), is(nullValue()));
    }

}
