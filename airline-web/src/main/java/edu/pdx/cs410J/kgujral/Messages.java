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

    public static String serverAirlineMismatch(String given, String expected )
    {
        return String.format("The airline stored on the server is different from what you provided. Given: %s | Expected: %s", given, expected );
    }

    public static  String extraneousArg(){
        return "Extraneous Arguments provided!";
    }

    public static  String errorConnectingServer(String hostname, String port){
        return String.format("There was an error while connecting to the host. " +
                "Please check whether the provided hostname %s and port %s are correct.", hostname, port);
    }

    public static String addedFlightTo(String flightNum, String airline){
        return String.format("Added Flight # %s to %s", flightNum, airline);
    }

    public static String allAirlineInfoDeleted() {
        return "All airline information has been deleted";
    }

    public static String noFlights(String src, String dest, String airline){
        return String.format("No flights were found from %s to %s for the airline %s", src, dest, airline);
    }

    public static String noAirline(String airline){
        return String.format("The airline %s is not present on the server", airline);
    }

}
