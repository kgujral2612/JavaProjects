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


  @VisibleForTesting
  static boolean isValidOption(String arg){
    return arg.equals("-print") || arg.equals("-README") ;
  }
  @VisibleForTesting
  static boolean isValidAirlineName(String airline){
    return airline !=null && !airline.isEmpty() && !airline.isBlank();
  }

  @VisibleForTesting
  static boolean isValidFlightNumber(String flightNumber){
    for(int i=0; i<flightNumber.length(); i++){
      if (!Character.isDigit(flightNumber.charAt(i)))
        return false;
    }
    return true;
  }

  @VisibleForTesting
  static boolean isValidAirportCode(String airportCode){
    for(int i=0; i<airportCode.length(); i++){
      if (!Character.isLetter(airportCode.charAt(i)))
        return false;
    }
    return airportCode.length() == 3;
  }

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
  @VisibleForTesting
  static Airline createAirline(String airline){ return new Airline(airline); }

  @VisibleForTesting
  static Flight createFlight(int flightNumber, String src, String dest, String depart, String arrive){ return new Flight(flightNumber, src, dest, depart, arrive); }

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


  public static void main(String[] args) {
    if(args == null || args.length == 0){
      System.err.println("Missing command line arguments");
      return;
    }

    HashMap<String, String> argMap = parseArguments(args);
    if(argMap == null){
      System.err.println("Please try again!");
      return;
    }
    if(argMap.size()==0){
      return;
    }

    var flight = createFlight(Integer.parseInt(argMap.get(argNames[1])), argMap.get(argNames[2]), argMap.get(argNames[3]), argMap.get(argNames[3]), argMap.get(argNames[4]));
    var airline = createAirline(argMap.get(argNames[0]));
    airline.addFlight(flight);

    if(argMap.get("print") != null){
      System.out.println(flight);
    }
  }
}