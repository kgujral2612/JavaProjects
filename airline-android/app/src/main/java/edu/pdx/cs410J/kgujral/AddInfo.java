package edu.pdx.cs410J.kgujral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddInfo extends AppCompatActivity {

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

    private Airline readForm() {
        String airlineName = ((EditText)findViewById(R.id.airlineNameTxtIp)).getText().toString();
        String flightNum = ((EditText)findViewById(R.id.flightNumberTxtIp)).getText().toString();
        String src = ((EditText)findViewById(R.id.srcTxtIp)).getText().toString();
        String dest = ((EditText)findViewById(R.id.destTxtIp)).getText().toString();
        String departure = ((EditText)findViewById(R.id.departureTxtIp)).getText().toString();
        String arrival = ((EditText)findViewById(R.id.arrivalTxtIp)).getText().toString();

        // perform validations

        // create airline object
        Airline airline = new Airline(airlineName);
        airline.addFlight(new Flight(
                Integer.parseInt(flightNum), src, dest,
                DateHelper.stringToDate(departure),
                DateHelper.stringToDate(arrival)));
        return airline;
    }

    private ArrayList<Airline> addAirline(ArrayList<Airline> airlines, Airline tempAirline){
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
            InternalStorageHelper internalStorageHelper = new InternalStorageHelper(getString(R.string.internalStoragePath), this);

            // get all airlines
            ArrayList<Airline> airlineArrayList = internalStorageHelper.readFromFile();
            if(airlineArrayList == null)
                airlineArrayList = new ArrayList();

            // append the flight to the airline if it exists
            airlineArrayList = addAirline(airlineArrayList, airline);

            // write to internal
            internalStorageHelper.writeToFile(airlineArrayList);
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
}