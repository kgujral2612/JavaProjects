package edu.pdx.cs410J.kgujral;
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
  static final String invalidArgument = "Invalid %s in text file! Was %s | Expected %s";
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
      try (
      BufferedReader br = new BufferedReader(this.reader)) {
          String line;
          int count=-1;
          int number=0;
          String src="", dest="", depart="", arrive;

          Airline airline = null;
          while ((line = br.readLine()) != null) {
            if(line.isEmpty() || line.isBlank()){
              continue;
            }
            line = line.trim();
            switch(count){
                case -1: if(!Project2.isValidAirlineName(line)){
                        return null;
                    }
                    airline = new Airline(line);
                    count ++;
                    break;
              case 0: if(!Project2.isValidFlightNumber(line)){
                          throw new ParserException(String.format(invalidArgument, "Flight Number", line, "A whole number. eg: 6478"));
                      }
                      number = Integer.parseInt(line);
                      count++;
                      break;
              case 1: if(!Project2.isValidAirportCode(line)){
                          throw new ParserException(String.format(invalidArgument, "Departure Airport Code", line, "A 3-letter String. eg: PDX"));
                      }
                      src = line;
                      count++;
                      break;
              case 2: if(!Project2.isValidDate(line.split(" ")[0])){
                            throw new ParserException(String.format(invalidArgument, "Departure Date", line, "A date in the format mm/dd/yyyy. eg: 11/03/1997"));
                      }
                      if(!Project2.isValidTime(line.split(" ")[1])){
                          throw new ParserException(String.format(invalidArgument, "Departure Time", line, "A time in the format hh:mm. eg: 05:45"));
                      }
                      depart = line;
                      count++;
                      break;
              case 3: if(!Project2.isValidAirportCode(line)){
                          throw new ParserException(String.format(invalidArgument, "Arrival Airport Code", line, "A 3-letter String. eg: PDX"));
                      }
                      dest = line;
                      count++;
                      break;
              case 4: if(!Project2.isValidDate(line.split(" ")[0])){
                          throw new ParserException(String.format(invalidArgument, "Arrival Date", line, "A date in the format mm/dd/yyyy. eg: 11/03/1997"));
                      }
                      if(!Project2.isValidTime(line.split(" ")[1])){
                          throw new ParserException(String.format(invalidArgument, "Arrival Time", line, "A time in the format hh:mm. eg: 05:45"));
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
