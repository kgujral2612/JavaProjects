package edu.pdx.cs410J.kgujral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddInfo extends AppCompatActivity {

    static String invalidIp = "Expected: %s | Given %s";
    static String invalidFlightDur = "Arrival must occur after Departure. " +
            "Given Arrival: %s, Departure: %s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        // Hide the Action Bar
        getSupportActionBar().hide();

        // Change the color of the status bar
        changeStatusBarColor();

        // call menu handler
        menuHandler();

        // add button functionality
        addButton();
    }

    /**
     * Reads the user input and performs validation
     * Create an airline object and returns.
     *
     * */
    private Airline readForm() {
        String airlineName = ((EditText)findViewById(R.id.airlineNameTxtIp)).getText().toString();
        String flightNum = ((EditText)findViewById(R.id.flightNumberTxtIp)).getText().toString();
        String src = ((EditText)findViewById(R.id.srcTxtIp)).getText().toString();
        String dest = ((EditText)findViewById(R.id.destTxtIp)).getText().toString();
        String departure = ((EditText)findViewById(R.id.departureTxtIp)).getText().toString();
        String arrival = ((EditText)findViewById(R.id.arrivalTxtIp)).getText().toString();

        // perform validations
        // 1. airlineName
        if(!ValidationHelper.isValidAirlineName(airlineName)) {
            openDialog("Invalid Airline Name", String.format(invalidIp, "A non-empty String. eg: British Airways", "empty or null"));
            ((EditText)findViewById(R.id.airlineNameTxtIp)).setText("");
            return null;
        }
        // create airline object
        Airline airline = new Airline(airlineName);

        // allow for empty airline addition
        if(flightNum.isEmpty() && src.isEmpty() && dest.isEmpty() && departure.isEmpty() && arrival.isEmpty()){
            return  airline;
        }

        // 2. flight number
        if(!ValidationHelper.isValidFlightNumber(flightNum)){
            if(flightNum == null || flightNum.isEmpty()) flightNum = "empty";
            openDialog("Invalid Flight Number", String.format(invalidIp, "A whole number. eg: 6478", flightNum));
            ((EditText)findViewById(R.id.flightNumberTxtIp)).setText("");
            return null;
        }
        // 3. src
        if(!ValidationHelper.isValidAirportCode(src) &&
                !ValidationHelper.isValidAirportName(src)){
            if(src == null || src.isEmpty()) src = "empty";
            openDialog("Invalid Source", String.format(invalidIp, "A 3-letter real-world airport code. eg: PDX", src));
            ((EditText)findViewById(R.id.srcTxtIp)).setText("");
            return null;
        }
        // 4. dest
        if(!ValidationHelper.isValidAirportCode(dest) &&
                !ValidationHelper.isValidAirportName(dest)){
            if(dest == null || dest.isEmpty()) dest = "empty";
            openDialog("Invalid Destination", String.format(invalidIp, "A 3-letter real-world airport code. eg: PDX", dest));
            ((EditText)findViewById(R.id.destTxtIp)).setText("");
            return null;
        }

        // 5. departure
        if(!ValidationHelper.isValidDateTime(departure)){
            if(departure == null || departure.isEmpty()) departure = "empty";
            openDialog("Invalid Departure Time", String.format(invalidIp, "A date in the format mm/dd/yyyy hh:mm. eg: 11/03/1997 11:30 PM", departure));
            ((EditText)findViewById(R.id.departureTxtIp)).setText("");
            return null;
        }
        // 6. Arrival
        if(!ValidationHelper.isValidDateTime(arrival)){
            if(arrival == null || arrival.isEmpty()) arrival = "empty";
            openDialog("Invalid Arrival Time", String.format(invalidIp, "A date in the format mm/dd/yyyy hh:mm. eg: 11/04/1997 10:15 AM", arrival));
            ((EditText)findViewById(R.id.arrivalTxtIp)).setText("");
            return null;
        }
        // 6. Flight Duration
        if(!ValidationHelper.isValidFlightDuration(departure, arrival)){
            openDialog("Invalid Flight Duration", String.format(invalidFlightDur, arrival, departure));
            ((EditText)findViewById(R.id.departureTxtIp)).setText("");
            ((EditText)findViewById(R.id.arrivalTxtIp)).setText("");
            return null;
        }

        airline.addFlight(new Flight(
                Integer.parseInt(flightNum), src, dest,
                DateHelper.stringToDate(departure),
                DateHelper.stringToDate(arrival)));
        return airline;
    }

    private void openDialog(String title, String message) {
        ErrorDialog dialog = new ErrorDialog();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show(getSupportFragmentManager(), "Error dialog");
    }

    private ArrayList<Airline> addAirline(ArrayList<Airline> airlines, Airline tempAirline){
        if(tempAirline.getFlights().toArray().length == 0){
            airlines.add(tempAirline);
            return airlines;
        }

        for (int i=0; i<airlines.size(); i++) {
            Airline airline = airlines.get(i);
            if(airline.getName().equals(tempAirline.getName())){
                // append flight to the airline
                airline.addFlight((Flight) tempAirline.getFlights().toArray()[0]);
                return airlines;
            }
        }
        airlines.add(tempAirline);
        return airlines;
    }

    private void addButton() {
        Button addBtn = findViewById(R.id.add_info_btn);
        addBtn.setOnClickListener(x -> {
            // read the form
            Airline airline = readForm();
            if(airline != null){
                InternalStorageHelper internalStorageHelper = new InternalStorageHelper(getString(R.string.internalStoragePath), this);

                // get all airlines
                ArrayList<Airline> airlineArrayList = internalStorageHelper.readFromFile();
                if(airlineArrayList == null)
                    airlineArrayList = new ArrayList();

                // append the flight to the airline if it exists
                airlineArrayList = addAirline(airlineArrayList, airline);

                // write to internal
                internalStorageHelper.writeToFile(airlineArrayList);

                // Toast
                if(airline.getFlights().size() == 0){
                    Toast.makeText(AddInfo.this, "Airline " + airline.getName() + " added successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Flight flight = (Flight) airline.getFlights().toArray()[0];
                    Toast.makeText(AddInfo.this, "Flight # " + Integer.toString(flight.getNumber()) +
                            " to airline " + airline.getName() + " added successfully", Toast.LENGTH_SHORT).show();
                }

                resetInputFields();

                closeKeyboard();
            }
        });
    }

    private void menuHandler() {
        MenuHandler menuHandler = new MenuHandler(this);
        menuHandler.buttonActions();
    }

    private void changeStatusBarColor(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }

    private void resetInputFields(){
        ((EditText)findViewById(R.id.airlineNameTxtIp)).setText("");
        ((EditText)findViewById(R.id.flightNumberTxtIp)).setText("");
        ((EditText)findViewById(R.id.srcTxtIp)).setText("");
        ((EditText)findViewById(R.id.destTxtIp)).setText("");
        ((EditText)findViewById(R.id.departureTxtIp)).setText("");
        ((EditText)findViewById(R.id.arrivalTxtIp)).setText("");
    }
    private void closeKeyboard(){
        RelativeLayout mainLayout;
        mainLayout = findViewById(R.id.parentLayoutAdd);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
    }
}