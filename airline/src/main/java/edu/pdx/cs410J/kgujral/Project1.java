package edu.pdx.cs410J.kgujral;
import com.google.common.annotations.VisibleForTesting;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  private static final String[] argNames = {"airline", "flightNumber", "src", "depart", "dest", "arrive"};
  private static final String invalidFlightMsg = "Invalid or missing flight number. Flight number must be numeric. For example: 4578";
  private static final String invalidSrcMsg = "Invalid or missing airport code for src. Airport codes must contain exactly 3 letter. For example: PDX";
  private static final String invalidDepartMsg = "Invalid or missing departure time. Departure time must be 2 separate command line arguments containing date and time in the format mm/dd/yyyy hh:mm. For example: 3/15/2023 10:39";
  private static final String invalidDestMsg = "Invalid or missing airport code for dest. Airport codes must contain exactly 3 letter. For example: SFO";
  private static final String invalidArriveMsg = "Invalid or missing arrival time. Arrival time must be 2 separate command line arguments containing date and time in the format mm/dd/yyyy hh:mm. For example: 3/15/2023 10:39";


  /**
  * Returns true if the option is valid,
  * i.e, either '-print' or '-README'
  * @param    option    an option that needs to be validated
  * @return             true if the option is valid, false otherwise
  * */
  @VisibleForTesting
  static boolean isValidOption(String option){
    return option.equals("-print") || option.equals("-README") ;
  }

  /**
   * Returns true if the airlineName is valid,
   * i.e, it should not be null, empty or blank
   * @param  airlineName  the name of an airline that needs to be validated
   * @return              true if airline is valid, false otherwise
   * */
  @VisibleForTesting
  static boolean isValidAirlineName(String airlineName){
    return airlineName !=null && !airlineName.isEmpty() && !airlineName.isBlank();
  }

  /**
   * Returns true if the flightNumber is valid,
   * i.e, it should be numeric
   * @param  flightNumber  a flight's number that needs to be validated
   * @return               true if flightNumber is valid, false otherwise
   * */
  @VisibleForTesting
  static boolean isValidFlightNumber(String flightNumber){
    for(int i=0; i<flightNumber.length(); i++){
      if (!Character.isDigit(flightNumber.charAt(i)))
        return false;
    }
    return true;
  }

  /**
   * Returns true if the airportCode is valid,
   * i.e, it should consist of letters and should be of length 3
   * @param  airportCode  airport code that needs to be validated
   * @return              true if airportCode is valid, false otherwise
   * */
  @VisibleForTesting
  static boolean isValidAirportCode(String airportCode){
    for(int i=0; i<airportCode.length(); i++){
      if (!Character.isLetter(airportCode.charAt(i)))
        return false;
    }
    return airportCode.length() == 3;
  }

  /**
   * Returns true if the dateAndTime is valid,
   * i.e, it should consist of date and time in
   * the format of mm/dd/yyyy hh:mm
   * @param  dateAndTime  a string consisting of date and time with space in between
   * @return              true if dateAndTime is valid, false otherwise
   * */
  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    dateFormat.setLenient(false);
    String[] splitDT = dateAndTime.split("\\s+");
    if(splitDT.length !=2)
      return false;
    try{
      dateFormat.parse(splitDT[0]);
      String[] splitT = splitDT[1].split(":");
      if(splitT.length !=2)
        return false;
      int hours = Integer.parseInt(splitT[0]);
      int minutes = Integer.parseInt(splitT[1]);
      if(hours > 24 || hours < 0 || minutes > 59 || minutes < 0 ){
        return false;
      }
    }
    catch(ParseException e){
      return false;
    }
    return true;
  }

  /**
   * Parses arguments
   * If -README option is selected, calls the readMe() method and exits
   * Otherwise, creates a hashmap mapping all the user arguments
   * after validating each one.
   * Assumes that the options precede the main arguments
   * Options may be '-print' or '-README'
   * Other arguments will be in the order: airline, flightNumber, src, depart, dest, arrive.
   * @param  args  an array of arguments
   * @return       a hashmap of mapped user arguments
   * @see #readMe()
   * */
  @VisibleForTesting
  static HashMap<String, String> parseArguments(String[] args){
    HashMap<String, String> argMap = new HashMap<>();
    int size = args.length;
    int i = 0;
    boolean optionsParsed = false;

    while(i<size){
      if(i <= 1 && !optionsParsed){
        if(isValidOption(args[i])){
          if(args[i].equals("-README")) //if readme is selected, print readme and do nothing else
          {
            try{
              System.out.println(readMe());
            }
            catch(IOException e){
              System.err.println("There was a problem in reading the README file");
            }
            return new HashMap<>();
          }
          argMap.put(args[i].substring(1), ""); //empty string indicates that an option was selected
          i++;
        }
        else
          optionsParsed = true; //wasn't a valid option; could be an argument
      }

      if(i > 1 || optionsParsed){
        if(size-i < 8){
          System.err.println("Some Arguments are missing in the input.");
          return null;
        }
        var airline = args[i++];
        argMap.put(argNames[0], airline);

        var flightNumber = args[i++];
        if(!isValidFlightNumber(flightNumber)){
          System.err.println(invalidFlightMsg);
          return null;
        }
        else
          argMap.put(argNames[1], flightNumber);

        var src = args[i++];
        if(!isValidAirportCode(src)){
          System.err.println(invalidSrcMsg);
          return null;
        }
        else
          argMap.put(argNames[2], src);

        var depart = args[i++] + " " + args[i++];
        if(!isValidDateAndTime(depart)){
          System.err.println(invalidDepartMsg);
          return null;
        }
        else
          argMap.put(argNames[3], depart);

        var dest = args[i++];
        if(!isValidAirportCode(dest)){
          System.err.println(invalidDestMsg);
          return null;
        }
        else
          argMap.put(argNames[4], dest);

        var arrive = args[i++] + " " + args[i++];
        if(!isValidDateAndTime(arrive)){
          System.err.println(invalidArriveMsg);
          return null;
        }
        else
          argMap.put(argNames[5], arrive);
      }
    }
    return argMap;
  }

  /**
   * Returns a string composed of the content
   * inside the project's README.txt file
   * @throws IOException if an input or output exception
   * occurs while reading the file.
   * @return  a string representing the content inside README.txt
   * */
  @VisibleForTesting
  static String readMe() throws IOException{
    StringBuilder content = new StringBuilder();
    try (InputStream readme = Project1.class.getResourceAsStream("README.txt")) {
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
   * Main control of the program
   * Receives user command line arguments
   * Processes arguments and orchestrates calling other functions
   * @param  args  user input (command-line arguments)
   * */
  public static void main(String[] args) {
    if(args == null || args.length == 0){
      System.err.println("Missing command line arguments");
      System.err.println("Please view how to use the program by running the command: \njava -jar target/airline-2023.0.0.jar -README\n");
      return;
    }

    HashMap<String, String> argMap = parseArguments(args);
    if(argMap == null){
      return;
    }
    if(argMap.size()==0){
      return;
    }

    var flight = new Flight(Integer.parseInt(argMap.get(argNames[1])), argMap.get(argNames[2]), argMap.get(argNames[3]), argMap.get(argNames[3]), argMap.get(argNames[4]));
    var airline = new Airline(argMap.get(argNames[0]));
    airline.addFlight(flight);

    if(argMap.get("print") != null){
      System.out.println(flight);
    }
  }
}