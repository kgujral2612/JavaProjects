package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A skeletal implementation of the {@link TextParser} class for {@link Project5}.
 */
public class TextParser implements AirlineParser<Airline> {

  /** The reader object that reads text files */
  private final Reader reader;
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
   * @throws ParserException when the file is either not
   * available or can't be parsed due to formatting issues
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
          case -1: airline = new Airline(line);
                  count ++;
                  break;
          case 0: number = Integer.parseInt(line);
                  count++;
                  break;
          case 1: src = line;
                  count++;
                  break;
          case 2: depart = line;
                  count++;
                  break;
          case 3: dest = line;
                  count++;
                  break;
          case 4: arrive = line;
                  airline.addFlight(new Flight(number, src, dest, DateHelper.stringToDate(depart), DateHelper.stringToDate(arrive)));
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
