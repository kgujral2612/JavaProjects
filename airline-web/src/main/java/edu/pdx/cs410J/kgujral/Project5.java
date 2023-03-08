package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.Collection;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */

public class Project5 {

    /** string containing host option */
    static final String hostOp = "-host";
    /** string containing host option */
    static final String portOp = "-port";
    /** string containing host option */
    static final String searchOp = "-search";
    /** string containing host option */
    static final String printOp = "-print";
    /** string containing host option */
    static final String readmeOp = "-README";


    public static void main(String... args) {
        if(args == null || args.length == 0){
            usage();
            return;
        }
        String hostName = null;
        String portString = null;
        String airlineName = null, searchAirlineName = null;
        String flightNumber = null;
        String src = null, searchSrc = null;
        String dest = null, searchDest = null;
        String depart = null;
        String arrive = null;

        String depDate = null, depTime = null, depMarker = null;
        String arrDate = null, arrTime = null, arrMarker = null;
        boolean shouldPrint = false;
        boolean shouldSearch = false;
        boolean shouldSearchWithAirline = true;


        for (int i=0; i<args.length; i++) {
            String arg = args[i];
            if(arg.equals(hostOp)){
             hostName = args[++i];
            }
            else if(arg.equals(portOp)){
                portString = args[++i];
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
            else if(arg.equals(searchOp)){
                shouldSearch = true;
                // either searchOp is succeeded by the Airline name
                // or it is succeeded by Airline name, src and dest airport codes
                searchAirlineName = args[++i];
            }
            else if(shouldSearch && searchSrc==null){
                searchSrc = arg;
                shouldSearchWithAirline = false;
                if(!ValidationHelper.isValidAirportCode(searchSrc) || !ValidationHelper.isValidAirportName(searchSrc)){
                    System.err.println(Messages.invalidArg("Source for searching", searchSrc, "A 3-letter real-world airport code. eg: PDX"));
                    return;
                }
            }
            else if(shouldSearch && searchDest==null){
                searchDest = arg;
                if(!ValidationHelper.isValidAirportCode(searchDest) || !ValidationHelper.isValidAirportName(searchDest)){
                    System.err.println(Messages.invalidArg("Destination for searching", searchDest, "A 3-letter real-world airport code. eg: PDX"));
                    return;
                }
            }
            else if (airlineName == null) {
                airlineName = arg;
                if(!ValidationHelper.isValidAirlineName(airlineName)){
                    System.err.println(Messages.invalidArg("Airline Name", airlineName, "A non-empty String. eg: British Airways"));
                    return;
                }

            } else if (flightNumber == null) {
                flightNumber = arg;
                if(!ValidationHelper.isValidFlightNumber(flightNumber)){
                    System.err.println(Messages.invalidArg("Flight Number", flightNumber, "A whole number. eg: 6478"));
                    return;
                }

            } else if (src == null) {
                src = arg;
                if(!ValidationHelper.isValidAirportCode(src) || !ValidationHelper.isValidAirportName(src)){
                    System.err.println(Messages.invalidArg("Source", src, "A 3-letter real-world airport code. eg: PDX"));
                    return;
                }

            } else if (depart == null) {
                if(depDate == null){
                    depDate = arg;
                    if(!ValidationHelper.isValidDate(depDate)){
                        System.err.println(Messages.invalidArg("Departure Date", depDate, "A date in the format mm/dd/yyyy. eg: 11/03/1997"));
                        return;
                    }
                }

                else if(depTime == null)
                {
                    depTime = arg;
                    if(!ValidationHelper.isValidTime(depTime)){
                        System.err.println(Messages.invalidArg("Departure Time", depTime, "12-hour time in the format hh:mm. eg: 08:05"));
                        return;
                    }

                }
                else if(depMarker == null)
                {
                    depMarker = arg;
                    if(!ValidationHelper.isValidTimeMarker(depMarker)){
                        System.err.println(Messages.invalidArg("Departure Time Marker", depMarker, "a marker after time: am/pm"));
                        return;
                    }
                    depart = depDate + " " + depTime + " " + depMarker;
                }

            }
            else if (dest == null) {
                dest = arg;
                if(!ValidationHelper.isValidAirportCode(dest) || !ValidationHelper.isValidAirportName(dest)){
                    System.err.println(Messages.invalidArg("Destination", dest, "A 3-letter real-world airport code. eg: PDX"));
                    return;
                }
            }
            else if (arrive == null) {
                if(arrDate == null){
                    arrDate = arg;
                    if(!ValidationHelper.isValidDate(arrDate)){
                        System.err.println(Messages.invalidArg("Arrival Date", arrDate, "A date in the format mm/dd/yyyy. eg: 11/03/1997"));
                        return;
                    }
                }

                else if(arrTime == null)
                {
                    arrTime = arg;
                    if(!ValidationHelper.isValidTime(arrTime)){
                        System.err.println(Messages.invalidArg("Arrival Time", arrTime, "12-hour time in the format hh:mm. eg: 08:05"));
                        return;
                    }

                }
                else if(arrMarker == null)
                {
                    arrMarker = arg;
                    if(!ValidationHelper.isValidTimeMarker(arrMarker)){
                        System.err.println(Messages.invalidArg("Arrival Time Marker", arrMarker, "a marker after time: am/pm"));
                        return;
                    }
                    arrive = arrDate + " " + arrTime + " " + arrMarker;
                }
            }
            else {
                System.err.println(Messages.extraneousArg());
                usage();
            }
        }

        if (hostName == null) {
            System.err.println(Messages.invalidArg("Host Name", null, "A host name, eg: localhost"));
            return;

        } else if ( portString == null) {
            System.err.println(Messages.invalidArg("Port Number", null, "A port number, eg: 8080"));
            return;
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            System.err.println(Messages.invalidArg("Port Number", portString, "A port number, eg: 8080"));
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        if(shouldSearch){
            //search with airline
            if(shouldSearchWithAirline){
                try{
                    Airline airlineFromClient = client.getAllFlights(searchAirlineName);
                    System.out.println(PrettyHelper.prettify(airlineFromClient));
                } catch (IOException | ParserException ex ) {
                    error("While contacting server: " + ex.getMessage());
                    return;
                }catch (Exception e) {
                    error(e.getMessage());
                    return;
                }
            }
            //search with airline, src and dest
            else{
                if(searchDest == null){
                    System.err.println(Messages.invalidArg("Destination for searching", null, "A 3-letter real-world airport code. eg: PDX"));
                    return;
                }
                try{
                    Airline airlineFromClient = new Airline(searchAirlineName);
                    Collection<Flight> flights = client.getFlightsBySrcAndDest(searchAirlineName, searchSrc, searchDest);
                    for(var flight: flights){
                        airlineFromClient.addFlight(flight);
                    }
                    System.out.println(PrettyHelper.prettify(airlineFromClient));
                } catch (IOException | ParserException ex ) {
                    error("While contacting server: " + ex.getMessage());
                    return;
                }catch (Exception e) {
                    error(e.getMessage());
                    return;
                }
            }

            return;
        }

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
                if(shouldPrint)
                    print(flight);
            }

        } catch (IOException | ParserException ex ) {
            error("While contacting server: " + ex.getMessage());
            return;
        } catch (Exception e) {
            error(e.getMessage());
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
     */
    private static void usage()
    {
        PrintStream err = System.err;
        err.println("You must provide some arguments to interact with the program!\n" +
                "usage: java -jar target/airline-client.jar [options] <args>\n" +
                "args are (in this order):\n" +
                "airline The name of the airline\n" +
                "flightNumber The flight number\n" +
                "src Three-letter code of departure airport\n" +
                "depart Departure date/time\n" +
                "dest Three-letter code of arrival airport\n" +
                "arrive Arrival date/time\n" +
                "-host hostname Host computer on which the server runs\n" +
                "-port port Port on which the server is listening\n" +
                "-search Search for flights\n" +
                "-print Prints a description of the new flight\n" +
                "-README Prints a README for this project and exits");
        err.println();
    }
}