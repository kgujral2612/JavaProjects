package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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
        if(day.isEmpty() || month.isEmpty() || year.isEmpty() || hours.isEmpty() || minute.isEmpty())
            return null;
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

            if(doc.getDocumentElement().getChildNodes().getLength() == 0){
                return null;
            }
            airline = new Airline(doc.getDocumentElement().getElementsByTagName("name").item(0).getTextContent());

            NodeList flightList = doc.getElementsByTagName("flight");
            try{
                for(int i=0; i<flightList.getLength(); i++){
                    Node node = flightList.item(i);
                    Element element = (Element) node;

                    String number, src, dest ;
                    number = element.getElementsByTagName("number").item(0).getTextContent();
                    if(!Project4.isValidFlightNumber(number))
                        throw new ParserException(String.format(invalidOrMissingEl, "Flight number", number, "a number like 6748"));

                    src = element.getElementsByTagName("src").item(0).getTextContent();
                    if(!Project4.isValidAirportCode(src) || !Project4.isValidAirportName(src))
                        throw new ParserException(String.format(invalidOrMissingEl, "Flight Source", src, "a 3-letter code like PDX"));

                    Element departureDate, arrivalDate ;
                    Date depart, arrive;
                    departureDate = (Element) element.getElementsByTagName("depart").item(0);
                    depart = getDateFromElement(departureDate);
                    if(depart == null)
                        throw new ParserException("The Departure Date and Time does not conform to the DTD.");

                    arrivalDate = (Element) element.getElementsByTagName("arrive").item(0);
                    arrive = getDateFromElement(arrivalDate);
                    if(arrive == null)
                        throw new ParserException("The Arrival Date and Time does not conform to the DTD.");

                    dest = element.getElementsByTagName("dest").item(0).getTextContent();
                    if(!Project4.isValidAirportCode(dest) || !Project4.isValidAirportName(dest))
                        throw new ParserException(String.format(invalidOrMissingEl, "Flight Destination", dest, "a 3-letter code like SFO"));

                    airline.addFlight(new Flight(Integer.parseInt(number), src, dest, depart, arrive));
                }
            }
            catch(ParserException e){
                throw e;
            }
            catch(Exception e){
                throw new ParserException("The XML file does not conform to the DTD.");
            }
            return airline;
        }
        catch(FileNotFoundException e){
            throw new RuntimeException("File was not found.");
        }
        catch(NullPointerException e){
            throw new ParserException("The XML file does not conform to the DTD.");
        }
        catch(SAXParseException e){
            return null;
        }
        catch(Exception e){
            throw new ParserException(e.getMessage());
        }
    }
}
