package edu.pdx.cs410J.kgujral;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project1Test {
  String[] validArgs = {"-print", "-README", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
  String[] validArgsPrintOp = {"-print", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
  String[] validArgsReadmeOp = {"-README", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
  String[] validArgsNoOp = {"My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};

  /**
   * Validation checking methods should return
   * true when the criteria are met and
   * false otherwise
   * */
  @Test
  void isValidOptionShouldReturnFalseIfInputIsInvalid(){
    String invalidOption = "-printing";
    assertThat(Project1.isValidOption(invalidOption), is(false));
  }
  @Test
  void isValidOptionShouldReturnTrueIfInputIsValid(){
    String validOption = "-print";
    assertThat(Project1.isValidOption(validOption), is(true));
    validOption = "-README";
    assertThat(Project1.isValidOption(validOption), is(true));
  }

  @Test
  void isValidAirlineNameShouldReturnFalseIfInputIsInvalid(){
    assertThat(Project1.isValidAirlineName(null), is(false));
    assertThat(Project1.isValidAirlineName(""), is(false));
    assertThat(Project1.isValidAirlineName(" "), is(false));
  }
  @Test
  void isValidAirlineNameShouldReturnTrueIfInputIsValid(){
    assertThat(Project1.isValidAirlineName("My Airline"), is(true));
  }

  @Test
  void isValidFlightNumberShouldReturnFalseIfInputIsInvalid(){
    String invalidFlightNumber = "A1C23";
    assertThat(Project1.isValidFlightNumber(invalidFlightNumber), is(false));
  }
  @Test
  void isValidFlightNumberShouldReturnTrueIfInputIsValid(){
    String validFlightNumber = "12345";
    assertThat(Project1.isValidFlightNumber(validFlightNumber), is(true));
  }

  @Test
  void isValidAirportCodeShouldReturnFalseIfInputIsInvalid(){
    String invalidAirport = "PORTLAND";
    assertThat(Project1.isValidAirportCode(invalidAirport), is(false));
  }
  @Test
  void isValidAirportCodeShouldReturnTrueIfInputIsValid(){
    String validAirport = "PDX";
    assertThat(Project1.isValidAirportCode(validAirport), is(true));
  }

  @Test
  void isValidDateAndTimeShouldReturnTrueIfInputIsValid(){
    String validDateTime = "3/15/2023 1:03";
    assertThat(Project1.isValidDateAndTime(validDateTime), is(true));
  }
  @Test
  void isValidDateAndTimeShouldReturnFalseIfInputIsInvalid(){
    String invalidDateTime = "3/15/2023";
    assertThat(Project1.isValidDateAndTime(invalidDateTime), is(false));
    invalidDateTime = "3/15/2023 25:09";
    assertThat(Project1.isValidDateAndTime(invalidDateTime), is(false));
    invalidDateTime = "15/3/2023 12:34";
    assertThat(Project1.isValidDateAndTime(invalidDateTime), is(false));
    invalidDateTime = "2023/3/20 12:33";
    assertThat(Project1.isValidDateAndTime(invalidDateTime), is(false));
    invalidDateTime = "2/3/2023 12 o'clock";
    assertThat(Project1.isValidDateAndTime(invalidDateTime), is(false));
  }

  /**
   * When valid arguments are passed, a map of arguments should be returned
   * */
  @Test
  void parseArgumentsWithValidArgAllOps(){
    var argMap = Project1.parseArguments(validArgs);
    assertThat(argMap, is(not(nullValue())));
  }

  /**
   * When valid arguments are passed along with '-print' option,
   * the key 'print' should be present
   * but the key 'README' should be null
   * */
  @Test
  void parseArgumentsWithValidArgPrintOps(){
    var argMap = Project1.parseArguments(validArgsPrintOp);
    assertThat(argMap, is(not(nullValue())));
    assertThat(argMap.size(), is(equalTo(7)));
    assertThat(argMap.get("print"), is(not(nullValue())));
    assertThat(argMap.get("README"), is(nullValue()));
  }

  /**
   * When valid arguments are passed along with '-print' and 'README' options,
   * an empty map should be returned
   * */
  @Test
  void parseArgumentsWithValidArgReadMeOps(){
    var argMap = Project1.parseArguments(validArgsReadmeOp);
    assertThat(argMap, is(not(nullValue())));
  }

  /**
   * When valid arguments are passed along with no options,
   * the keys 'print' and 'README' should not be present
   * */
  @Test
  void parseArgumentsWithValidArgNoOps(){
    var argMap = Project1.parseArguments(validArgsNoOp);
    assertThat(argMap, is(not(nullValue())));
    assertThat(argMap.size(), is(equalTo(6)));
    assertThat(argMap.get("print"), is(nullValue()));
    assertThat(argMap.get("README"), is(nullValue()));
  }

  /**
   * When arguments contain invalid flight data
   * a null map should be returned
   * */
  @Test
  void parseArgumentsWithInvalidFlight(){
    String[] invalidFlight = {"My Awesome Airways", "Flight1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    var argMap = Project1.parseArguments(invalidFlight);
    assertThat(argMap,is(nullValue()));
  }

  /**
   * When arguments contain invalid src data
   * a null map should be returned
   * */
  @Test
  void parseArgumentsWithInvalidSrc(){
    String[] invalidSrc = {"My Awesome Airways", "1234", "Portland", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    var argMap = Project1.parseArguments(invalidSrc);
    assertThat(argMap,is(nullValue()));
  }

  /**
   * When arguments contain invalid dest data
   * a null map should be returned
   * */
  @Test
  void parseArgumentsWithInvalidDest(){
    String[] invalidDest = {"My Awesome Airways", "123", "PDX", "3/15/2023", "1:03", "San Francisco", "3/15/2023", "3:33"};
    var argMap = Project1.parseArguments(invalidDest);
    assertThat(argMap,is(nullValue()));
  }

  /**
   * When arguments contain invalid depart data
   * a null map should be returned
   * */
  @Test
  void parseArgumentsWithInvalidDepart(){
    String[] invalidDepart = {"My Awesome Airways", "123", "PDX", "3/15/2023", "", "SFO", "3/15/2023", "3:33"};
    var argMap = Project1.parseArguments(invalidDepart);
    assertThat(argMap,is(nullValue()));
  }

  /**
   * When arguments contain invalid arrive data
   * a null map should be returned
   * */
  @Test
  void parseArgumentsWithInvalidArrive(){
    String[] invalidArrive = {"My Awesome Airways", "123", "PDX", "3/15/2023", "1:03", "SFO", "2023/15/20", "3:33"};
    var argMap = Project1.parseArguments(invalidArrive);
    assertThat(argMap,is(nullValue()));
  }

  /**
   * the readMe() method should read README.txt
   * as a project resource and return the content
   * in form of a string
   * */
  @Test
  void readMeShouldReturnContentOfReadMeFile(){
    try{
      var content = Project1.readMe();
      assertThat(content, containsString("CS501 Project1: Designing an Airline Application"));
      assertThat(content, containsString("- Kaushambi Gujral"));
      assertThat(content, containsString("This project helps the user input data for an airline and its single flight."));
      assertThat(content, containsString("The airline data should consist of:"));
      assertThat(content, containsString("- airline: The name of the airline"));
      assertThat(content, containsString("The flight data should consist of:"));
      assertThat(content, containsString("- flightNumber: The flight number (numeric)"));
      assertThat(content, containsString("- src: Three-letter code of departure airport"));
      assertThat(content, containsString("- depart: Departure date and time (24-hour time in mm/dd/yyy hh:mm format)"));
      assertThat(content, containsString("- dest: Three-letter code of arrival airport"));
      assertThat(content, containsString("- arrive: Arrival date and time (24-hour time in mm/dd/yyy hh:mm format)"));
    }
    catch(IOException e){
      var expected = "";
      assertThat(expected, containsString("Readme could not be read!"));
    }
  }
}
