===============================================================
CS501 Project 4:: Storing Airlines as XML
- Kaushambi Gujral

This project helps the user input data for an airline and its single flight.
The airline's flights will be sorted and the user will have the ability to "pretty print" the flights in an airline.
The user also has the option to read/write airline data using text files or xml files.
---------------------------------------------------------------
An airline needs the below data:
* name
a flight needs the below data:
* flight-number (is a whole number eg: 5678)
* departure airport code (contains 3 letters eg: PDX and is a valid, real world airport code)
* departure date and time (12 hr format: mm/dd/yyyy hh:mm eg: 01/31/2022 08:22 am)
* arrival airport code (contains 3 letters eg: SEA and is a valid, real world airport code)
* arrival date and time (12 hr format: mm/dd/yyyy hh:mm eg: 01/31/2022 18:22 pm)

These data must be supplied to the program in the exact sequence as above. If any datum is invalid or missing, the program will identify it and display an appropriate error message.
Eg: java -jar target/airline-2023.0.0.jar "Test Airline" 1234 PDX 2/2/1995 10:00 am SFO 2/2/1995 12:45 am

The user can add options while calling the program. Any combination of options can be provided and the sequence doesn't matter.
* -print : prints the details of the new flight
    Eg: java -jar target/airline-2023.0.0.jar -print "My Airline" 1234 PDX 2/2/1995 10:00 am SFO 2/2/1995 12:45 am
* -README : prints the README.txt for the project
    Eg: java -jar target/airline-2023.0.0.jar -README
* -pretty "file": Pretty print the airline's flights to a text file with the path "file"
* -pretty - : Pretty print the airline's flights to standard out
* -textfile "FilePath" : Adds the new flight information into the file present at "FilePath". If the file has not been created, the program creates a new text file. Alternatively, if the file exists, the airline name in the file must match that in the argument.
    Eg: java -jar target/airline-2023.0.0.jar -textFile "file.txt" "My Airline" 1234 PDX 2/2/1995 10:00 am SFO 2/2/1995 12:45 am
    For an existent file, the format should be as follows:
    [airline name]
    [flight number]
    [departure airport code]
    [departure date and time]
    [arrival airport code]
    [arrival date and time]
    Eg:
    Alaska Airlines
    6791
    PDX
    11/01/2023 05:45 am
    SFO
    11/01/2023 08:30 am
* -xmlFile "FilePath":Adds the new flight information onto the specified xml file.
===============================================================
