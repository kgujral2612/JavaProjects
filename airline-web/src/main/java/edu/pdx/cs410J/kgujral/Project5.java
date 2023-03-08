package edu.pdx.cs410J.kgujral;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */

public class Project5 {

    /** string containing missing args message */
    public static final String MISSING_ARGS = "Missing command line arguments";
    /** string containing textFile option */
    static final String hostOp = "-host";
    static final String portOp = "-port";
    static final String searchOp = "-search";
    static final String printOp = "-print";
    static final String readmeOp = "-README";


    public static void main(String... args) {
        if(args == null || args.length == 0){
            usage("You must provide some arguments to interact with the program!");
            return;
        }
        String hostName = null;
        String portString = null;
        String airlineName = null;
        String flightNumber = null;
        String src = null;
        String dest = null;
        String depart = null;
        String arrive = null;

        String depDate = null, depTime = null, depMarker = null;
        String arrDate = null, arrTime = null, arrMarker = null;
        boolean shouldPrint = false;


        for (int i=0; i<args.length; i++) {
            String arg = args[i];
            if(arg.equals(hostOp)){
             hostName = args[++i];
            }
            else if(arg.equals(portOp)){
                portString = args[++i];
            }
            else if(arg.equals(searchOp)){

            }
            else if(arg.equals(printOp)){
                shouldPrint = true;
            }
            else if (arg.equals(readmeOp)){
                try {
                    readMe();
                    return;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (airlineName == null) {
                airlineName = arg;

            } else if (flightNumber == null) {
                flightNumber = arg;

            } else if (src == null) {
                src = arg;

            } else if (depart == null) {
                if(depDate == null)
                    depDate = arg;
                else if(depTime == null)
                    depTime = arg;
                else if(depMarker == null)
                {
                    depMarker = arg;
                    depart = depDate + " " + depTime + " " + depMarker;
                }

            }
            else if (dest == null) {
                dest = arg;
            }
            else if (arrive == null) {
                if(arrDate == null)
                    arrDate = arg;
                else if(arrTime == null)
                    arrTime = arg;
                else if(arrMarker == null)
                {
                    arrMarker = arg;
                    arrive = arrDate + " " + arrTime + " " + arrMarker;
                }

            }
            else {
                usage("Extraneous command line argument: " + arg);
            }
        }

        if (hostName == null) {
            usage( MISSING_ARGS );
            return;

        } else if ( portString == null) {
            usage( "Missing port" );
            return;
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        String message = "";

        try {
            Flight flight = new Flight(Integer.parseInt(flightNumber),
                    src, dest, DateHelper.stringToDate(depart), DateHelper.stringToDate(arrive));

            client.addFlight(airlineName, flight);

            Airline airlineFromClient = client.getAllFlights(airlineName);
            if (airlineFromClient == null) {
                message = "Could not add flight info\n";

            } else {
                message = Messages.addedFlightTo(flightNumber, airlineName);
            }

        } catch (IOException | ParserException ex ) {
            error("While contacting server: " + ex.getMessage());
            return;
        } catch (Exception e) {
            error("Some error " + e.getMessage());
            return;
        }

        System.out.println(message);
    }

    /** Prints out project readme
     * @throws IOException if file cannot be read
     * */
    private static void readMe() throws IOException{
        StringBuilder content = new StringBuilder();
        try (InputStream readme = Project5.class.getResourceAsStream("README.txt")) {
            if(readme == null)
                return;
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line;
            while((line = reader.readLine())!=null){
                content.append(line);
                content.append("\n");
            }
        }
        System.out.println(content);
    }

    /** Prints a description of the new flight
     * @param flight the flight whose description needs to be printed
     * */
    private static void print(Flight flight) {
        System.out.println(flight);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project5 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();
    }
}