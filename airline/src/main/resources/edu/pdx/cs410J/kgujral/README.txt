CS501 Project1: Designing an Airline Application
- Kaushambi Gujral

This project helps the user input data for an airline and its single flight.

The airline data should consist of:
- airline: The name of the airline

The flight data should consist of:
- flightNumber: The flight number (numeric)
- src: Three-letter code of departure airport
- depart: Departure date and time (24-hour time in mm/dd/yyy hh:mm format)
- dest: Three-letter code of arrival airport
- arrive: Arrival date and time (24-hour time in mm/dd/yyy hh:mm format)

Code Usage:
java -jar target/airline-2023.0.0.jar [options] <args>

Examples

Command: java -jar target/airline-2023.0.0.jar "My Airline" 1234 PDX 2/2/1995 10:00 SFO 2/2/1995 12:45
Output:

Command: java -jar target/airline-2023.0.0.jar -print "My Airline" 1234 PDX 2/2/1995 10:00 SFO 2/2/1995 12:45
Output: Flight 1234 departs PDX at 2/2/1995 10:00 arrives 2/2/1995 10:00 at SFO


