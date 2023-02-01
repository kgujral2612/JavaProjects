package edu.pdx.cs410J.kgujral;
import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A skeletal implementation of the {@link TextParser} class for {@link Project2}.
 */
public class TextParser implements AirlineParser<Airline> {

    /** The reader object that reads text files */
  private final Reader reader;
    /** missing or invalid argument message */
  static final String missingOrInvalidArgument = "Missing or Invalid %s in text file! Was %s | Expected %s";
    /** io error message */
  static final String ioError = "Unable to read the given text file.";

    /** Parameterized constructor that
     * accepts a reader object and
     * assigns it to the class reader data member
     * @param reader the reader object
     * */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * An override method
   * that parses text files searching for a
   * single airline and its flights
   * @return Airline along with its flight information
   * */
  @Override
  public Airline parse() throws ParserException {
      boolean airlineNameNotPresent = false;
      boolean flightInfoPresent = false;
      try (
      BufferedReader br = new BufferedReader(this.reader)) {
          String airlineName = br.readLine();

          if(!Project2.isValidAirlineName(airlineName)){
              airlineNameNotPresent = true;
              return null;
          }

          String line;
          int count=0;
          int number=0;
          String src="", dest="", depart="", arrive;

          var airline = new Airline(airlineName);
          while ((line = br.readLine()) != null) {
            if(line.isEmpty() || line.isBlank()){
              continue;
            }
            line = line.trim();
            switch(count){
              case 0: if(!Project2.isValidFlightNumber(line)){
                          throw new ParserException(String.format(missingOrInvalidArgument, "Flight Number", line, "A whole number. eg: 6478"));
                      }
                      number = Integer.parseInt(line);
                      count++;
                      break;
              case 1: if(!Project2.isValidAirportCode(line)){
                          throw new ParserException(String.format(missingOrInvalidArgument, "Departure Airport Code", line, "A 3-letter String. eg: PDX"));
                      }
                      src = line;
                      count++;
                      break;
              case 2: if(!Project2.isValidDate(line.split(" ")[0])){
                            throw new ParserException(String.format(missingOrInvalidArgument, "Departure Date", line, "A date in the format mm/dd/yyyy. eg: 11/03/1997"));
                      }
                      if(!Project2.isValidTime(line.split(" ")[1])){
                          throw new ParserException(String.format(missingOrInvalidArgument, "Departure Time", line, "A time in the format hh:mm. eg: 05:45"));
                      }
                      depart = line;
                      count++;
                      break;
              case 3: if(!Project2.isValidAirportCode(line)){
                          throw new ParserException(String.format(missingOrInvalidArgument, "Arrival Airport Code", line, "A 3-letter String. eg: PDX"));
                      }
                      dest = line;
                      count++;
                      break;
              case 4: if(!Project2.isValidDate(line.split(" ")[0])){
                          throw new ParserException(String.format(missingOrInvalidArgument, "Arrival Date", line, "A date in the format mm/dd/yyyy. eg: 11/03/1997"));
                      }
                      if(!Project2.isValidTime(line.split(" ")[1])){
                          throw new ParserException(String.format(missingOrInvalidArgument, "Arrival Time", line, "A time in the format hh:mm. eg: 05:45"));
                      }
                      arrive = line;
                      airline.addFlight(new Flight(number, src, dest, depart, arrive));
                      count=0;
                      break;
            }
          }
          return airline;
    }
    catch (IOException e) {
      throw new ParserException(ioError);
    }
  }
}
