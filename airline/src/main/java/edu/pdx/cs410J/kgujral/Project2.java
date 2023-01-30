package edu.pdx.cs410J.kgujral;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Project2 {

    static final String textFileOp = "-textFile";
    static final String readMeOp = "-README";
    static final String printOp = "-print";
    static final String tooFewArguments = "You must provide all the 8 arguments";
    static final String tooManyArguments = "You must not provide more than 8 arguments";
    @VisibleForTesting
    static String readMe() throws IOException {
        StringBuilder content = new StringBuilder();
        try (InputStream readme = Project2.class.getResourceAsStream("README2.txt")) {
            if(readme == null)
                return null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line;
            while((line = reader.readLine())!=null){
                content.append(line);
                content.append("\n");
            }
        }
        return content.toString();
    }

    @VisibleForTesting
    static int getArgumentIndex(String[] args){
        for(int i=0; i<args.length; i++){
            if(args[i].startsWith("-")){
                if(args[i].equals(textFileOp))
                    i++;
            }
            else
                return i;
        }
        return 0;
    }

    static void parseArguments(String[] args, int idxTill) throws IOException{
        boolean shouldPrint = false;
        boolean shouldUseTextFile = false;
        String filePath = "";

        for(int i =0; i<idxTill; i++){
            if(args[i].equals(readMeOp)){
                readMe();
                return;
            }

            if(args[i].equals(printOp)){
                shouldPrint = true;
            }
            if(args[i].equals(textFileOp)){
                shouldUseTextFile = true;
                filePath = args[i++];
            }
        }

        if(args.length - idxTill < 8){
            System.err.println(tooFewArguments);
            return;
        }
        if(args.length - idxTill >8){
            System.err.println(tooManyArguments);
            return;
        }

        int idxFrom = idxTill;
        Airline airline = null;
        int number =0;
        String src="", dest="", depart="", arrive="";

        for(int i=idxFrom, j=0; i<args.length; i++, j++){
            switch (j){
                case 0: airline = new Airline(args[i]);
                        break;
                case 1: number = Integer.parseInt(args[i]);
                        break;
                case 2: src = args[i];
                        break;
                case 3: depart  = args[i];
                        break;
                case 4: dest = args[i];
                        break;
                case 5: arrive = args[i];
                        break;
            }
        }
        airline.addFlight(new Flight(number, src, dest, depart, arrive));
        if(shouldPrint){

        }
        if(shouldUseTextFile){}
    }


    public static void main(String[] args){
        System.out.println("Welcome to Project 2");
    }
}
