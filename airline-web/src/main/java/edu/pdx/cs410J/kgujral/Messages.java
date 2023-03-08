package edu.pdx.cs410J.kgujral;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String invalidArg(String argName, String given, String expected )
    {
        return String.format("Invalid argument %s . Given: %s | Expected: %s", argName, given, expected );
    }

    public static  String extraneousArg(){
        return "Extraneous Arguments provided!";
    }

    public static String addedFlightTo(String flightNum, String airline){
        return String.format("Added Flight # %s to %s", flightNum, airline);
    }

    public static String allAirlineInfoDeleted() {
        return "All airline information has been deleted";
    }

}
