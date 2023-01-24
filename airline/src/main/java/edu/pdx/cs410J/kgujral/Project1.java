package edu.pdx.cs410J.kgujral;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.util.HashMap;


/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  private static final String[] argNames = {"airline", "flightNumber", "src", "depart", "dest", "arrive"};
  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }


  public static HashMap<String, String> parseArguments(String[] args){
    HashMap<String, String> argMap = new HashMap<>();
    for(int i=0, j=0; i < args.length; i++){
      if(args[i] == null){
        j++;
        continue;
      }
      if(args[i].startsWith("-"))
        argMap.put(args[i].substring(1), ""); //empty string indicates that an option was selected
      else{
        argMap.put(argNames[j++], args[i]);
      }
    }
    return argMap;
  }
  public static int findMissingArguments(HashMap<String, String> argMap){
    StringBuilder message = new StringBuilder();
    message.append("Missing argument(s):\n");
    int i = 0;
    for(String arg : argNames){
      if(argMap.get(arg) == null){
        message.append(++i + ". ");
        message.append(arg);
        message.append("\n");
      }
    }
    if(i>0){
      System.err.println(message);
    }
    return i;
  }
  public static Airline createAirline(HashMap<String, String> argMap){
    var airlineName = argMap.get("airline");
    if(airlineName== null || airlineName.isEmpty())
      return null;
    return new Airline(airlineName);
  }

  public static Flight createFlight(HashMap<String, String> argMap){
    var flightNumber = Integer.parseInt(argMap.get("flightNumber"));
    var src = argMap.get("src");
    var depart = argMap.get("depart");
    var dest = argMap.get("dest");
    var arrive = argMap.get("arrive");

    return new Flight(flightNumber, src, dest, depart, arrive);
  }

  public static String readFile(String path){
    File file = new File(path);
    StringBuilder content = new StringBuilder();
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String str = "";
      while((str = br.readLine()) !=null){
        content.append(str);
      }
    } catch (FileNotFoundException e) {
      System.out.println("Could not find the file.");
    } catch (IOException e) {
      System.out.println("Issues while reading the file.");
    }
    return content.toString();
  }

  public static void main(String[] args) {
    if(args == null || args.length == 0){
      System.err.println("Missing command line arguments");
      return;
    }

    for(String arg: args){
      System.out.println(arg);
    }



//    parseArguments(args);
//    if(findMissingArguments() > 0)
//      System.exit(0);
//
//    //Everything is fine, continue
//    var airline = createAirline();
//    var flight = createFlight();
//    if(argMap.get("print") !=null){
//      //Prints a description of the new flight
//      airline.toString();
//      flight.toString();
//    }
//    if(argMap.get("README") !=null){
//     //Prints a README for this project and exits
//      try{
//
//      }
//      catch(Exception e){
//
//      }
//      finally{
//        System.exit(0);
//      }
//    }
  }
}