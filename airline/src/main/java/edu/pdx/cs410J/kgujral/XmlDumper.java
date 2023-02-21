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
    private static PrintWriter err;
    private PrintWriter pw;

    public XmlDumper(String fileName) throws IOException {
        this(new File(fileName));
    }

    public XmlDumper(File file) throws IOException {
        this(new PrintWriter(new FileWriter(file), true));
    }

    public XmlDumper(PrintWriter pw) {
        this.pw = pw;
    }

    private Element getDateElement(Document doc, Date dateTime){
        Element date = doc.createElement("date");
        var dateBreakDown = DateHelper.breakdownDate(dateTime);
        date.setAttribute("day", dateBreakDown.getDay());
        date.setAttribute("month", dateBreakDown.getMonth());
        date.setAttribute("year", dateBreakDown.getYear());

        return date;
    }

    private Element getTimeElement(Document doc, Date dateTime){
        Element date = doc.createElement("time");
        var dateBreakDown = DateHelper.breakdownDate(dateTime);
        date.setAttribute("hour", dateBreakDown.getHours());
        date.setAttribute("minute", dateBreakDown.getMinutes());

        return date;
    }
    @Override
    public void dump(Airline airline) throws IOException {
        Document doc = null;
        String s;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(this);
            builder.setEntityResolver(this);
            DOMImplementation dom = builder.getDOMImplementation();
            DocumentType docType = dom.createDocumentType("airline", "-//Portland State University//DTD CS410J Airline//EN", "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");
            doc = dom.createDocument(null, "airline", docType);
        } catch (ParserConfigurationException var17) {
            s = "Illconfigured XML parser";
        } catch (DOMException var18) {
            s = "While creating XML Document";
        }
        Element airlineEl = doc.getDocumentElement();
        Element airlineName = doc.createElement("name");
        airlineName.setTextContent(airline.getName());
        airlineEl.appendChild(airlineName);

        for(Iterator flights = airline.getFlights().iterator(); flights.hasNext(); ) {
            Flight flight = (Flight) flights.next();
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
            Transformer xform = xFactory.newTransformer();
            xform.setOutputProperty("indent", "yes");
            xform.setOutputProperty("doctype-system", "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");
            xform.setOutputProperty("doctype-public", "-//Portland State University//DTD CS410J Airline//EN");
            xform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            xform.setOutputProperty("encoding", "ASCII");
            xform.transform(src, res);
        } catch (TransformerException var16) {
            s = "While transforming XML";
        }

        this.pw.flush();
    }
}
