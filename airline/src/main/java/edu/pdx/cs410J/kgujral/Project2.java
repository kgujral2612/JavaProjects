package edu.pdx.cs410J.kgujral;
import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main class for the CS410J airline Project
 */
public class Project2 {
    /** string containing textFile option */
    static final String textFileOp = "-textFile";
    /** string containing README option */
    static final String readMeOp = "-README";
    /** string containing print option */
    static final String printOp = "-print";
    /** string containing invalid argument message */
    static final String invalidArgument = "Invalid %s! Was %s | Expected %s";
    /** string containing too many argument message*/
    static final String tooManyArguments = "You must not provide more than 8 arguments";
    /** string containing date and time format  */
    static final String datetimeFormat = "%s %s";
    /** string containing could not load readme message */
    static final String couldNotLoadReadMe = "Unable to lead README.txt file";
    /** string containing airline mismatch message  */
    static final String airlineNameMismatch = "The name of the airline specified in the arguments is different from that in the file. Was %s | Expected %s ";
    /** string containing missing CLI arguments message  */
    static final String missingCLIArguments = "Missing command line arguments";
    /** string containing missing arguments message */
    static final String missingArguments = "These items were missing from your input: ";
    /** string containing io error message */
    static final String ioError = "Unable to write to the file %s";

    /**
     * Returns a string composed of the content
     * inside the project's README.txt file
     * @throws IOException if an input or output exception
     * occurs while reading the file.
     * @return  a string representing the content inside README.txt
     * */
    @VisibleForTesting
    static String readMe() throws IOException {
        StringBuilder content = new StringBuilder();
        try (InputStream readme = Project2.class.getResourceAsStream("README.txt")) {
            if(readme == null)
                return null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line;
            while((line = reader.readLine())!=null){
                content.append(line);
                content.append("\n");
            }
        }
        return content.toString();
    }

    /**
     * Prints a flight
     * @param flight
     * */
    @VisibleForTesting
    static void print(Flight flight){
        System.out.println(flight);
    }

    /**
     * Parses the file to gather airline information
     * If the airline is same as the airline provided
     * in the arguments, the new flight information
     * is appended inside the file
     * In case the file is not found, it creates a new file
     * In case the file has formatting errors,
     * it provides user with a troubleshooting message
     * @param textFile the path to the textFile
     * @param airline the airline whose name should be matched with the contents of the textFile
     * @param flight the flight that needs to be added to the airline in the text file
     * */
    @VisibleForTesting
    static void textFile(String textFile, Airline airline, Flight flight){
        TextParser parser;
        Airline airlineFromFile ;
        try {
            parser = new TextParser(new FileReader(textFile));
            try{
                airlineFromFile = parser.parse();
                if(!airlineFromFile.getName().equals(airline.getName())){
                    System.err.printf((airlineNameMismatch) + "%n", airline.getName(), airlineFromFile.getName());
                    return;
                }
            }
            catch (ParserException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
        catch (FileNotFoundException e) {
            airlineFromFile = airline;
        }

        airlineFromFile.addFlight(flight);
        try{
            TextDumper dumper = new TextDumper(new FileWriter(textFile));
            dumper.dump(airlineFromFile);
        }
        catch(IOException e){
            System.err.printf((ioError) + "%n", textFile);
        }
    }

    /**
     * Returns true if the airlineName is valid,
     * i.e, it should not be null, empty or blank
     * @param  airlineName  the name of an airline that needs to be validated
     * @return              true if airline is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidAirlineName(String airlineName){
        return airlineName != null && !airlineName.isEmpty() && !airlineName.isBlank();
    }

    /**
     * Returns true if the flightNumber is valid,
     * i.e, it should be numeric
     * @param  flightNumber  a flight's number that needs to be validated
     * @return               true if flightNumber is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidFlightNumber(String flightNumber){
        try{
            int num = Integer.parseInt(flightNumber);
            return num >= 0;
        }
        catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Returns true if the airportCode is valid,
     * i.e, it should consist of letters and should be of length 3
     * @param  airportCode  airport code that needs to be validated
     * @return              true if airportCode is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidAirportCode(String airportCode){
        if(airportCode == null)
            return false;
        for(int i=0; i<airportCode.length(); i++){
            if (!Character.isLetter(airportCode.charAt(i)))
                return false;
        }
        return airportCode.length() == 3;
    }

    /**
     * Returns true if the date is valid,
     * i.e, in the format of mm/dd/yyyy
     * @param  date  a string consisting of date
     * @return  true if date is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidDate(String date){
        if(date == null || date.isEmpty())
            return false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try{
            dateFormat.parse(date);
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }

    /**
     * Returns true if the time is valid,
     * i.e, in the format of hh:mm
     * @param  time  contains time
     * @return  true if date is valid, false otherwise
     * */
    @VisibleForTesting
    static boolean isValidTime(String time){
        if(time == null || time.isEmpty())
            return false;
        try{
            String[] splitT = time.split(":");
            if(splitT.length != 2)
                return false;
            int hours = Integer.parseInt(splitT[0]);
            int minutes = Integer.parseInt(splitT[1]);
            return hours <= 24 && hours >= 0 && minutes <= 59 && minutes >= 0;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Returns the index where the arguments
     * begin and options end.
     * @param  args  contains the user arguments
     * @return  the index from where the
     * arguments begin
     * */
    @VisibleForTesting
    static int getArgumentIndex(String[] args){
        int i=0;
        while(i<args.length){
            if(args[i].startsWith("-")){
                if(args[i].equals(textFileOp))
                    i++;
            }
            else
                break;
            i++;
        }
        return i;
    }

    /**
     * Parses options and returns
     * a map indicating the ones
     * that were present
     * @param  args  contains the user arguments
     * @return  a hashmap indicating which
     * options were present
     * */
    @VisibleForTesting
    static HashMap<String, String> parseOptions(String[] args){
        int optionIdxTill = getArgumentIndex(args);
        HashMap<String, String> argMap = new HashMap<>();
        for(int i=0; i<optionIdxTill; i++){
            switch (args[i]) {
                case readMeOp:
                    argMap.put("readme", "true");
                    break;
                case printOp:
                    argMap.put("print", "true");
                    break;
                case textFileOp:
                    var filePath = args[++i];
                    if(!filePath.endsWith(".txt"))
                        filePath += ".txt";
                    argMap.put("textFile", filePath);
                    break;
            }
        }
        return argMap;
    }

    /**
     * Parses options and returns
     * a map indicating the ones
     * that were present
     * @param  args  contains the user arguments
     * @return  a hashmap indicating which
     * options were present
     * */
    @VisibleForTesting
    static HashMap<String, String> parseArgs(String[] args){
        HashMap<String, String> argMap = new HashMap<>();
        int idxFrom = getArgumentIndex(args);

        boolean argsAreMissing = false;
        var missingList = new ArrayList<String>();
        if(args.length - idxFrom < 8){
            argsAreMissing = true;
        }
        for(int i=idxFrom, j=0; i<args.length; i++, j++){
            switch (j){
                case 0: if(!isValidAirlineName(args[i])){
                            if(argsAreMissing){
                                missingList.add("Airline Name");
                                i--; continue;
                            }
                            else {
                            System.err.printf((invalidArgument) + "%n", "Airline Name", args[i], "A non-empty String. eg: British Airways");
                            break;
                            }
                        }
                    argMap.put("airline", args[i]);
                    break;
                case 1: if(!isValidFlightNumber(args[i])){
                            if(argsAreMissing){
                                missingList.add("Flight Number");
                                i--; continue;
                        }
                        else {
                            System.err.printf((invalidArgument) + "%n", "Flight Number", args[i], "A whole number. eg: 6478");
                            break;
                        }
                    }
                    argMap.put("flightNumber", args[i]);
                    break;
                case 2: if(!isValidAirportCode(args[i])){
                        if(argsAreMissing){
                            missingList.add("Departure Airport Code");
                            i--; continue;
                        }
                        else {
                            System.err.printf((invalidArgument) + "%n", "Departure Airport Code", args[i], "A 3-letter String. eg: PDX");
                            break;
                        }
                    }
                    argMap.put("src", args[i]);
                    break;
                case 3: if(!isValidDate(args[i])){
                        if(argsAreMissing){
                            missingList.add("Date of Departure");
                            i--; continue;
                        }
                        else{
                            System.err.printf((invalidArgument) + "%n", "Date of Departure", args[i], "A date in the format mm/dd/yyyy. eg: 11/03/1997");
                            break;
                        }
                    }
                    argMap.put("departDate", args[i]);
                    break;
                case 4: if(!isValidTime(args[i])){
                        if(argsAreMissing){
                            missingList.add("Time of Departure");
                            i--; continue;
                        }
                        else{
                            System.err.printf((invalidArgument) + "%n", "Time of Departure", args[i], "A time in the format hh:mm. eg: 05:45");
                            break;
                        }
                    }
                    argMap.put("departTime", args[i]);
                    break;
                case 5: if(!isValidAirportCode(args[i])){
                    if(argsAreMissing){
                        missingList.add("Arrival Airport Code");
                        i--; continue;
                    }
                    else {
                        System.err.printf((invalidArgument) + "%n", "Arrival Airport Code", args[i], "A 3-letter String. eg: SFO");
                        break;
                    }
                }
                    argMap.put("dest", args[i]);
                    break;
                case 6: if(!isValidDate(args[i])){
                    if(argsAreMissing){
                        missingList.add("Date of Arrival");
                        i--; continue;
                    }
                    else {
                        System.err.printf((invalidArgument) + "%n", "Date of Arrival", args[i], "A date in the format mm/dd/yyyy. eg: 11/03/1997");
                        break;
                    }
                }
                    argMap.put("arriveDate", args[i]);
                    break;
                case 7: if(!isValidTime(args[i])){
                    System.err.printf((invalidArgument) + "%n", "Time of Arrival", args[i], "A time in the format hh:mm. eg: 08:05");
                    break;
                    }
                    argMap.put("arriveTime", args[i]);
                    break;
            }
        }
        if(argMap.size() < 8 && argMap.get("arriveTime") == null){
            missingList.add("Time of Arrival");
        }
        if(missingList.size()>0){
            System.err.print(missingArguments);
            for(String missingItem: missingList){
                System.err.print(missingItem + "; ");
            }
            System.err.println();
        }
        return argMap;
    }

    /**
     * Orchestrates the code flow by calling various methods.
     * Gets parsed options, and parsed as well as validated arguments.
     * Creates airline and flight objects.
     * If -README option is selected, displays the README.txt for the project and exits
     * If -print option is selected, displays the new flight information
     * If -textFile option is selected, add new flight information to the airline stored inside the file
     * @param  args  user input (command-line arguments)
     * */
    @VisibleForTesting
    static void handleArguments(String[] args){
        var opMap = parseOptions(args);
        if(opMap.get("readme")!= null){
            try{
                System.out.println(readMe());
            }
            catch(IOException e){
                System.err.println(couldNotLoadReadMe);
            }
            return;
        }

        int opCount = opMap.get("textFile")!=null? opMap.size()+1 : opMap.size();
        if(args.length - opCount > 8){

            System.err.println(tooManyArguments);
            return;
        }

        Airline airline;
        Flight flight;
        var argMap = parseArgs(args);
        if(argMap.size() == 8){
            airline = new Airline(argMap.get("airline"));
            flight = new Flight(Integer.parseInt(argMap.get("flightNumber")), argMap.get("src"), argMap.get("dest"),
                    String.format(datetimeFormat, argMap.get("departDate"), argMap.get("departTime")),
                    String.format(datetimeFormat, argMap.get("arriveDate"), argMap.get("arriveTime")));

            if(opMap.get("print")!=null)
                print(flight);

            if(opMap.get("textFile")!=null)
                textFile(opMap.get("textFile"), airline, flight);
        }
    }

    /**
     * Prints program usage
     * */
    @VisibleForTesting
    static void printUsage(){
        StringBuilder msg = new StringBuilder();
        msg.append("java -jar target/airline-2023.0.0.jar [options] <args>\n");
        msg.append("\nargs are (in this order):\n");
        msg.append("airline\tThe name of the airline\n");
        msg.append("flightNumber\tThe flight number\n");
        msg.append("src\tThree-letter code of departure airport\n");
        msg.append("depart\tDeparture date and time (24-hour time)\n");
        msg.append("dest\tThree-letter code of arrival airport\n");
        msg.append("arrive\tArrival date and time (24-hour time)\n");
        msg.append("\noptions are (options may appear in any order):\n");
        msg.append("-textFile file\tWhere to read/write the airline info\n");
        msg.append("-print\tPrints a description of the new flight\n");
        msg.append("-README\tPrints a README for this project and exits\n");
        msg.append("Dates and times should be in the format: mm/dd/yyyy hh:mm\n");
        System.out.println(msg);
    }

    /**
     * Main control of the program
     * Receives user command line arguments
     * If user arguments are not provided, displays the usage.
     * If user arguments are provided, calls handleArguments() method
     * @param  args  user input (command-line arguments)
     * */
    public static void main(String[] args){
        if(args == null || args.length == 0){
            System.err.println(missingCLIArguments);
            printUsage();
            return;
        }
        handleArguments(args);
    }
}
