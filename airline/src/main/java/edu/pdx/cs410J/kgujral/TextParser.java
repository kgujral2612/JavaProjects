package edu.pdx.cs410J.kgujral;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A skeletal implementation of the <code>TextParser</code> class for Project 2.
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  @Override
  public Airline parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)) {

      String airlineName = br.readLine();
      if (airlineName == null) {
        throw new ParserException("Missing airline name");
      }
      String line;
      int count=0;
      int number=0;
      String src="", dest="", depart="", arrive="";

      var airline = new Airline(airlineName);
      while ((line = br.readLine()) != null) {
        if(line.isEmpty() || line.isBlank()){
          continue;
        }
        switch(count){
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
                  airline.addFlight(new Flight(number, src, dest, depart, arrive));
                  count=0;
                  break;
        }
      }
      return airline;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
