package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) { return invokeMain( Project1.class, args ); }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  /**
   * Tests that invoke the main method with
   * valid arguments don't issue an error
   */
  @Test
  void noErrorMessageWhenArgumentsAreValid(){
      String[] validArgs = {"Some Valid Airline", "1234", "PDX", "03/04/2022", "16:00", "SFO", "03/04/2022", "19:00"};
      MainMethodResult result = invokeMain(validArgs);
      assertThat(result.getTextWrittenToStandardError(), is(equalTo("")));
  }

  /**
   * When main method is called with some
   * invalid arguments, it displays a helpful message
   */
  @Test
  void shouldPrintMessageWhenFlightNumberIsInvalid(){
    String[] args = {"Some Valid Airline", "Flight1234", "PDX", "03/04/2022", "16:00", "SFO", "03/04/2022", "19:00"};
    MainMethodResult result = invokeMain(args);
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid or missing flight number. Flight number must be numeric. For example: 4578"));
    assertThat(result.getTextWrittenToStandardError(), containsString("Please try again!"));
  }

  @Test
  void shouldPrintMessageWhenSrcIsInvalid(){
    String[] args = {"Some Valid Airline", "1234", "Portland", "03/04/2022", "16:00", "SFO", "03/04/2022", "19:00"};
    MainMethodResult result = invokeMain(args);
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid or missing airport code for src. Airport codes must contain exactly 3 letter. For example: PDX"));
    assertThat(result.getTextWrittenToStandardError(), containsString("Please try again!"));
  }

  @Test
  void shouldPrintMessageWhenDestIsInvalid(){
    String[] args = {"Some Valid Airline", "1234", "PDX", "03/04/2022", "16:00", "San Francisco", "03/04/2022", "19:00"};
    MainMethodResult result = invokeMain(args);
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid or missing airport code for dest. Airport codes must contain exactly 3 letter. For example: SFO"));
    assertThat(result.getTextWrittenToStandardError(), containsString("Please try again!"));
  }

  @Test
  void shouldPrintMessageWhenDepartIsInvalid(){
    String[] args = {"Some Valid Airline", "1234", "PDX", "03-04-2022", "16:00", "SFO", "03/04/2022", "19:00"};
    MainMethodResult result = invokeMain(args);
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid or missing departure time. Departure time must be 2 separate command line arguments containing date and time in the format mm/dd/yyyy hh:mm. For example: 3/15/2023 10:39"));
    assertThat(result.getTextWrittenToStandardError(), containsString("Please try again!"));
  }

  @Test
  void shouldPrintMessageWhenArriveIsInvalid(){
    String[] args = {"Some Valid Airline", "1234", "PDX", "03/04/2022", "16:00", "SFO", "2022/04/21", "19:00"};
    MainMethodResult result = invokeMain(args);
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid or missing arrival time. Arrival time must be 2 separate command line arguments containing date and time in the format mm/dd/yyyy hh:mm. For example: 3/15/2023 10:39"));
    assertThat(result.getTextWrittenToStandardError(), containsString("Please try again!"));
  }

  @Test
  void shouldPrintMessageWhenSomeArgumentsAreMissing(){
    String[] args = {"-print", "Some Valid Airline", "1234"};
    MainMethodResult result = invokeMain(args);
    assertThat(result.getTextWrittenToStandardError(), containsString("Some Arguments are missing in the input."));
    assertThat(result.getTextWrittenToStandardError(), containsString("Please try again!"));
  }

  /**
   * Tests that invoke the main method with
   * '-print' option should print flight details
   */
  @Test
  void printsDescriptionOfNewFlightWithOption(){
    String[] validArgs = {"-print","Some Valid Airline", "1234", "PDX", "03/04/2022", "16:00", "SFO", "03/04/2022", "19:00"};
    MainMethodResult result = invokeMain(validArgs);
    assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 1234 departs PDX at 03/04/2022 16:00 arrives 03/04/2022 16:00 at SFO"));
  }

  /**
   * Tests that invoke the main method with
   * '-README' option should print the README for the project and exit
   */
  @Test
  void printsReadmeWithOption(){
    String[] validArgs = {"-README","Some Valid Airline", "1234", "PDX", "03/04/2022", "16:00", "SFO", "03/04/2022", "19:00"};
    MainMethodResult result = invokeMain(validArgs);
    var content = result.getTextWrittenToStandardOut();
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
    assertThat(result.getTextWrittenToStandardError(), is(equalTo("")));
  }

  /**
   * If both options are given, only print out the README and end program.
   * */
  @Test
  void printsReadmeWhenBothOptionsAreGiven(){
    String[] validArgs = {"-README","-print","Some Valid Airline", "1234", "PDX", "03/04/2022", "16:00", "SFO", "03/04/2022", "19:00"};
    MainMethodResult result = invokeMain(validArgs);
    var content = result.getTextWrittenToStandardOut();
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
    assertThat(result.getTextWrittenToStandardError(), is(equalTo("")));
  }
}