===============================================================
CS501 Project 5::  A REST-ful Airline Web Service
- Kaushambi Gujral

This project helps the user interact with an airline server.
The user can enter data for an airline and its single flight and can query flight information by source and destination.
---------------------------------------------------------------
An airline needs the below data:
* name
a flight needs the below data:
* flight-number (is a whole number eg: 5678)
* departure airport code (contains 3 letters eg: PDX and is a valid, real world airport code)
* departure date and time (12 hr format: mm/dd/yyyy hh:mm eg: 01/31/2022 08:22 am)
* arrival airport code (contains 3 letters eg: SEA and is a valid, real world airport code)
* arrival date and time (12 hr format: mm/dd/yyyy hh:mm eg: 01/31/2022 18:22 pm)

These data must be supplied to the program in the exact sequence as above.

The user can provide options to their program in any sequence:
-host hostname: Host computer on which the server runs
-port port: Port on which the server is listening
-search: Search for flights
-print: Prints a description of the new flight
-README: Prints a README for this project and exits

EXAMPLES

(1) Add a flight to the server:
$ java -jar target/airline-client.jar -host localhost -port 12345 "AirDave" 123 PDX 07/19/2023 1:02 pm ORD 07/19/2023 6:22 pm

(2) Search for a flight between two airports. The below command line should pretty-print all direct
flights that originate at PDX and terminate at LAS. A message should be printed if there is no
direct flight between the specified airports.
$ java -jar target/airline-client.jar -host localhost -port 12345 -search "AirDave" PDX LAS

(3) Pretty print all flights in an airline.
$ java -jar target/airline-client.jar -host localhost -port 12345 -search "AirDave"
===============================================================
