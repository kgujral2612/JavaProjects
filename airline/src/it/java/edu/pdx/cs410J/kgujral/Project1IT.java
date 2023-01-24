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
   * Tests that invoke the main method with valid arguments don't issue an error
   * and prints the arguments as Standard Output
   */
  @Test
  void noErrorMessageWhenArgumentsAreValid(){
      //Arrange
      String[] validArgs = {"AirLine", "1234", "PDX", "16:00", "SFO", "19:00"};

      //Act
      MainMethodResult result = invokeMain(validArgs);

      //Assert
      assertThat(result.getTextWrittenToStandardError(), is(""));
      for (var arg: validArgs) {
          assertThat(result.getTextWrittenToStandardOut(), containsString(arg));
      }
  }

  /**
   * Tests that invoke the main method with '-print' option should print flight details
   */
  @Test
  void printsDescriptionOfNewFlightWithOption(){
      //Arrange
      String[] validArgs = {"AirLine", "1234", "PDX", "16:00", "SFO", "19:00"};

      //Act
      MainMethodResult result = invokeMain(validArgs);
  }
  /**
   * Tests that invoke the main method with '-README' option
   */
  @Test
  void printsReadmeWithOption(){
        //Project1.main(null);
  }

}