package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AirlineParser;

import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * A skeletal implementation of the {@link XmlParser} class for {@link Project4}.
 */
public class XmlParser implements AirlineParser<Airline> {

    private static final String invalidOrMissingEl = "%s was missing or invalid. Was %s | Expected %s";
    private final File file;
    public XmlParser(File filePath){
            file = filePath;
    }
    /** utility method that parses the date time node element and creates a date*/
    private Date getDateFromElement(Element dateTime){
        String day = dateTime.getElementsByTagName("date").item(0).getAttributes().item(0).getTextContent();
        String month = dateTime.getElementsByTagName("date").item(0).getAttributes().item(1).getTextContent();
        String year = dateTime.getElementsByTagName("date").item(0).getAttributes().item(2).getTextContent();
        String hours = dateTime.getElementsByTagName("time").item(0).getAttributes().item(0).getTextContent();
        String minute = dateTime.getElementsByTagName("time").item(0).getAttributes().item(1).getTextContent();
        return DateHelper.dateElementsToDate(day, month, year, hours, minute);
    }
    @Override
    public Airline parse() throws ParserException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            Airline airline;

            try{airline = new Airline(doc.getDocumentElement().getElementsByTagName("name").item(0).getTextContent());}
            catch(Exception e){throw new ParserException(String.format(invalidOrMissingEl, "Airline Name", null, "a non-empty string. Eg: British Airways"));}

            NodeList flightList = doc.getElementsByTagName("flight");
            try{
                for(int i=0; i<flightList.getLength(); i++){
                    Node node = flightList.item(i);
                    Element element = (Element) node;

                    String number = null, src = null, dest = null;
                    try{number = element.getElementsByTagName("number").item(0).getTextContent();}
                    catch(NullPointerException e){throw new ParserException(String.format(invalidOrMissingEl, "Flight number", number, "a number like 6748")); }
                    if(!Project4.isValidFlightNumber(number))
                        throw new ParserException(String.format(invalidOrMissingEl, "Flight number", number, "a number like 6748"));

                    try{src = element.getElementsByTagName("src").item(0).getTextContent();}
                    catch(NullPointerException e){ throw new ParserException(String.format(invalidOrMissingEl, "Flight Source", src, "a 3-letter code like PDX")); }
                    if(!Project4.isValidAirportCode(src) || !Project4.isValidAirportName(src))
                        throw new ParserException(String.format(invalidOrMissingEl, "Flight Source", src, "a 3-letter code like PDX"));

                    Element departureDate = null, arrivalDate = null;
                    Date depart, arrive;
                    try{departureDate = (Element) element.getElementsByTagName("depart").item(0);
                        depart = getDateFromElement(departureDate);}
                    catch(NullPointerException e){throw new ParserException(String.format(invalidOrMissingEl, "Departure Date Time", departureDate, "24-hour date time in form of attributes."));}
                    catch (Exception e){throw new ParserException(String.format(invalidOrMissingEl, "Departure Date Time", departureDate, "24-hour date time in form of attributes.")); }

                    try{arrivalDate = (Element) element.getElementsByTagName("arrive").item(0);
                         arrive = getDateFromElement(arrivalDate);}
                    catch (NullPointerException e){throw new ParserException(String.format(invalidOrMissingEl, "Arrival Date Time", arrivalDate, "24-hour date time in form of attributes."));}
                    catch (Exception e){throw new ParserException(String.format(invalidOrMissingEl, "Arrival Date Time", arrivalDate, "24-hour date time in form of attributes.")); }

                    try{dest = element.getElementsByTagName("dest").item(0).getTextContent();}
                    catch (NullPointerException e){throw new ParserException(String.format(invalidOrMissingEl, "Flight Source", dest, "a 3-letter code like SFO")); }
                    if(!Project4.isValidAirportCode(dest) || !Project4.isValidAirportName(dest))
                        throw new ParserException(String.format(invalidOrMissingEl, "Flight Destination", dest, "a 3-letter code like SFO"));

                    airline.addFlight(new Flight(Integer.parseInt(number), src, dest, depart, arrive));
                }
            }
            catch(ParserException e){
                throw new ParserException(e.getMessage());
            }
            return airline;
        }
        catch (ParserException e){
            throw new ParserException(e.getMessage());
        }
        catch(FileNotFoundException e){
            return null;
        }
        catch(Exception e){
            throw new ParserException("There was a problem parsing the xml file :-(");
        }
    }
}
