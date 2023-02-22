package edu.pdx.cs410J.kgujral;
import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class XmlDumper extends AirlineXmlHelper implements AirlineDumper<Airline> {
    private final PrintWriter pw;

    /** Parameterized constructor
     * @param file the File object for the XML file*/
    public XmlDumper(File file) throws IOException {
        this(new PrintWriter(new FileWriter(file), true));
    }
    /** Parameterized constructor
     * @param  pw PrintWriter object that
     *           will help in writing to the XML file*/
    public XmlDumper(PrintWriter pw) {
        this.pw = pw;
    }

    /** Converts the date to XML DOM elements
     * @param doc the XML doc
     * @param dateTime the Date object*/
    private Element getDateElement(Document doc, Date dateTime){
        Element date = doc.createElement("date");
        var dateBreakDown = DateHelper.breakdownDate(dateTime);
        date.setAttribute("day", dateBreakDown.getDay());
        date.setAttribute("month", dateBreakDown.getMonth());
        date.setAttribute("year", dateBreakDown.getYear());

        return date;
    }

    /** Converts the time to XML DOM elements
     * @param doc the XML doc
     * @param dateTime the Date object containing time*/
    private Element getTimeElement(Document doc, Date dateTime){
        Element date = doc.createElement("time");
        var dateBreakDown = DateHelper.breakdownDate(dateTime);
        date.setAttribute("hour", dateBreakDown.getHours());
        date.setAttribute("minute", dateBreakDown.getMinutes());
        return date;
    }
    @Override
    public void dump(Airline airline) throws IOException {
        if(airline == null)
        {
            System.err.println("The airline to be dumped inside the XML file is null");
            return;
        }
        Document doc ;
        try {

            AirlineXmlHelper helper = new AirlineXmlHelper();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);
            DOMImplementation dom = builder.getDOMImplementation();
            DocumentType docType = dom.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
            doc = dom.createDocument(null, "airline", docType);
        } catch (ParserConfigurationException var17) {
            System.err.println("Ill-configured XML parser");
            return;
        } catch (DOMException var18) {
            System.err.println("The system faced an error while creating XML Document");
            return;
        }
        Element airlineEl = doc.getDocumentElement();
        Element airlineName = doc.createElement("name");
        airlineName.setTextContent(airline.getName());
        airlineEl.appendChild(airlineName);

        for (Flight flight : airline.getFlights()) {
            Element flightEl = doc.createElement("flight");

            Element num = doc.createElement("number");
            num.setTextContent(String.valueOf(flight.getNumber()));
            flightEl.appendChild(num);

            Element src = doc.createElement("src");
            src.setTextContent(flight.getSource());
            flightEl.appendChild(src);

            Element departure = doc.createElement("depart");
            departure.appendChild(getDateElement(doc, flight.getDeparture()));
            departure.appendChild(getTimeElement(doc, flight.getDeparture()));
            flightEl.appendChild(departure);

            Element dest = doc.createElement("dest");
            dest.setTextContent(flight.getDestination());
            flightEl.appendChild(dest);

            Element arrival = doc.createElement("arrive");
            arrival.appendChild(getDateElement(doc, flight.getArrival()));
            arrival.appendChild(getTimeElement(doc, flight.getArrival()));
            flightEl.appendChild(arrival);

            airlineEl.appendChild(flightEl);
        }
        try {
            Source src = new DOMSource(doc);
            Result res = new StreamResult(this.pw);
            TransformerFactory xFactory = TransformerFactory.newInstance();
            Transformer xForm = xFactory.newTransformer();
            xForm.setOutputProperty("indent", "yes");
            xForm.setOutputProperty("doctype-system", "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");
            xForm.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            xForm.setOutputProperty("encoding", "ASCII");
            xForm.transform(src, res);
        } catch (TransformerException var16) {
            System.err.println("The system faced an error while transforming XML");
            return;
        }

        this.pw.flush();
    }
}
