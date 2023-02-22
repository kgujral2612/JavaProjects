package edu.pdx.cs410J.kgujral;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/** Class to convert text file into xml file*/
public class Converter {

    /** string containing missing CLI arguments message  */
    static final String missingCLIArguments = "Missing command line arguments";
    /** string containing file not found message  */
    static final String fileNotFound = "The file location given by you %s cannot be found.";

    /** string containing unsuccessful xml dump message*/
    static final String xmlDumpUnsuccessful = "Unable to dump into the XML file.";
    /** string containing airline null message*/
    static final String airlineNull = "The airline parsed from the given text file was null.";

    /** string containing too few CLI arguments message  */
    static final String tooFewArgs = "Too few args. Was %s args | Expected 2 arguments, i.e textFilePath and xmlFilePath";

    /** string containing too many CLI arguments message  */
    static final String tooManyArgs = "Too many args. Was %s args | Expected 2 arguments, i.e textFilePath and xmlFilePath";
    /**
     * Prints program usage
     * */
    @VisibleForTesting
    static void printUsage(){
        String msg = "java edu.pdx.cs410J.<login-id>.Converter textFile xmlFile";
        System.out.println(msg);
    }
    /** Reads amd returns the airline from the given text file
     * @param textFile the location of the text file
     * @return Airline object read from the file*/
    @VisibleForTesting
    static Airline readAirline(String textFile){
        textFile = textFile.endsWith(".txt")? textFile: textFile+".txt";
        TextParser parser;
        Airline airlineFromFile = null;
        try {
            parser = new TextParser(new FileReader(textFile));
            try{
                airlineFromFile = parser.parse();
            }
            catch (ParserException e) {
                System.err.println(e.getMessage());
                return null;
            }
        }
        catch (FileNotFoundException e) {
            System.err.printf((fileNotFound) + "%n", textFile);
        }
        return airlineFromFile;
    }
    /** Reads and parses the contents of a text file
     * and dumps them into a xml file
     * @param airline the Airline object
     * @param xmlFile the location of the xmlFile*/
    @VisibleForTesting
    static void dumpAirlineToXml(Airline airline, String xmlFile) {
        xmlFile = xmlFile.endsWith(".xml")? xmlFile: xmlFile+".xml";
        if(airline == null){
            System.err.println(airlineNull);
            return;
        }
        try{
            XmlDumper dumper = new XmlDumper(new File(xmlFile));
            dumper.dump(airline);
        }
        catch(IOException e){
            System.err.println(xmlDumpUnsuccessful);
        }
    }
    /**
     * Main control of the program
     * Receives user command line arguments
     * */
    public static void main(String[] args){
        if(args == null || args.length == 0){
            System.err.println(missingCLIArguments);
            printUsage();
            return;
        }
        if(args.length < 2)
        {
            System.err.printf((tooFewArgs) + "%n", args.length);
            return;
        }
        if(args.length > 2)
        {
            System.err.printf((tooManyArgs) + "%n", args.length);
            return;
        }
        dumpAirlineToXml(readAirline(args[0]), args[1]);
    }
}
