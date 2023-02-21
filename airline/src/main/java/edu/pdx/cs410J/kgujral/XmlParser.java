package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AirlineParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Date;

/**
 * A skeletal implementation of the {@link XmlParser} class for {@link Project4}.
 */
public class XmlParser implements AirlineParser<Airline> {

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
    public Airline parse() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            Airline airline = new Airline(doc.getDocumentElement().getElementsByTagName("name").item(0).getTextContent());

            NodeList flightList = doc.getElementsByTagName("flight");
            for(int i=0; i<flightList.getLength(); i++){
                Node node = flightList.item(i);
                Element element = (Element) node;
                String number = element.getElementsByTagName("number").item(0).getTextContent();
                String src = element.getElementsByTagName("src").item(0).getTextContent();
                Element departureDate = (Element) element.getElementsByTagName("depart").item(0);
                var depart = getDateFromElement(departureDate);
                Element arrivalDate = (Element) element.getElementsByTagName("arrive").item(0);
                var arrive = getDateFromElement(arrivalDate);
                String dest = element.getElementsByTagName("dest").item(0).getTextContent();
                airline.addFlight(new Flight(Integer.parseInt(number), src, dest, depart, arrive));
            }
            return airline;
        }
        catch(Exception e){

        }
        return null;
    }
}
