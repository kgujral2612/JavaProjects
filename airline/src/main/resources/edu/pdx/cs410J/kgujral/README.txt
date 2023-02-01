===============================================================
CS501 Project2: : Storing An Airline in a Text File
- Kaushambi Gujral

This project helps the user input data for an airline and its single flight.
An airline needs the below data:
* name
whereas a flight needs the below data:
* flight-number (is a whole number eg: 5678)
* departure airport code (contains 3 letters eg: PDX)
* departure date and time (24 hr format: mm/dd/yyyy hh:mm eg: 01/31/2022 08:22)
* arrival airport code (contains 3 letters eg: SEA)
* arrival date and time (24 hr format: mm/dd/yyyy eg: hh:mm 01/31/2022 18:22)

These data must be supplied to the program in the exact sequence as above. If any datum is invalid or missing, the program will identify it and display an appropriate error message.
Eg: java -jar target/airline-2023.0.0.jar "My Airline" 1234 PDX 2/2/1995 10:00 SFO 2/2/1995 12:45

The user can add options while calling the program. Any combination of options can be provided and the sequence doesn't matter.
* -print : prints the details of the new flight
    Eg: java -jar target/airline-2023.0.0.jar -print "My Airline" 1234 PDX 2/2/1995 10:00 SFO 2/2/1995 12:45
* -README : prints the README.txt for the project
    Eg: java -jar target/airline-2023.0.0.jar -README
* -textfile "FilePath" : Adds the new flight information into the file present at "FilePath". If the file has not been created, the program creates a new text file. Alternatively, if the file exists, the airline name in the file must match that in the argument.
    Eg: java -jar target/airline-2023.0.0.jar -textFile "file.txt" "My Airline" 1234 PDX 2/2/1995 10:00 SFO 2/2/1995 12:45

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
    28/01/2023 19:00
    SFO
    28/01/2023 20:30
    6792
    SFO
    28/01/2023 17:23
    PDX
    28/01/2023 20:53

The below command is also valid:
java -jar target/airline-2023.0.0.jar -print -README "My Airline" 1234 PDX 2/2/1995 10:00 SFO 2/2/1995 12:45
===============================================================
