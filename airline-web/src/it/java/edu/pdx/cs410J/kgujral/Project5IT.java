package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */

@Disabled
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    /** When the args contains -README option,
     * Readme should be printed and the
     * program should exit*/
    @Test
    void userInputWithReadme(){
        String[] args = new String[]{"-host", "localhost", "-README", "-port", "8080", "Test Airline", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        MainMethodResult result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
        assertThat(result.getTextWrittenToStandardOut(), containsString("CS501 Project 5::  A REST-ful Airline Web Service"));
    }

    /**
     * When no args are provided,
     * program usage should be printed
     * */
    @Test
    void printUsage(){
        String[] args = new String[]{};
        MainMethodResult result = invokeMain( Project5.class, args);
        assertThat(result.getTextWrittenToStandardError(), containsString("You must provide some arguments to interact with the program"));
    }


    /** When a data element is invalid,
     * should not post info to the serve*/
    @Test
    void shouldNotPostNewFlightInfoWithInvalidFlightNumber(){

        String[] args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "3453", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        MainMethodResult result = invokeMain( Project5.class, args);

    }

    /** When a data element is invalid,
     * should not post info to the serve*/
    @Test
    void shouldNotPostNewFlightInfoWithInvalidSrc(){

        String[] args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "3453", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        MainMethodResult result = invokeMain( Project5.class, args);

    }

    /** When a data element is invalid,
     * should not post info to the serve*/
    @Test
    void shouldNotPostNewFlightInfoWithInvalidDeparture(){

        String[] args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "3453", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        MainMethodResult result = invokeMain( Project5.class, args);

    }
    /** When a data element is invalid,
     * should not post info to the serve*/
    @Test
    void shouldNotPostNewFlightInfoWithInvalidDest(){

        String[] args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "3453", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        MainMethodResult result = invokeMain( Project5.class, args);

    }

    /** When a data element is invalid,
     * should not post info to the serve*/
    @Test
    void shouldNotPostNewFlightInfoWithInvalidArrival(){

        String[] args = new String[]{"-host", "localhost", "-port", "8080",
                "Test Airline", "3453", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
        MainMethodResult result = invokeMain( Project5.class, args);

    }

    /** When all args are valid,
     * should post info to the serve*/
   @Test
   void shouldPostNewFlightInfoToServer(){

       String[] args = new String[]{"-host", "localhost", "-port", "8080",
               "Test Airline", "3453", "PDX", "07/19/2023", "1:02", "pm", "ORD", "07/19/2023", "6:22", "pm"};
       MainMethodResult result = invokeMain( Project5.class, args);

   }

   /** Should print */

    /** Should search by airline*/

    /** Should search by src*/

    /** Should search by src and dest*/
}