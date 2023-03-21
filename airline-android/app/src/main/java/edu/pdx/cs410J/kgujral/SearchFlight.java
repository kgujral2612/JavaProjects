package edu.pdx.cs410J.kgujral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchFlight extends AppCompatActivity {

    static String invalidIp = "Expected: %s | Given %s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Hide the Action Bar
        getSupportActionBar().hide();

        // Change the color of the status bar
        changeStatusBarColor();

        // call menu handler
        menuHandler();

        // search button functionality
        searchButton();
    }

    private void searchButton(){
        Button addBtn = findViewById(R.id.search_flights_btn);
        addBtn.setOnClickListener(x -> {
            searchFlights();
            closeKeyboard();
        });
    }
    private void searchFlights(){
        String airlineName = ((EditText)findViewById(R.id.airlineNameTxtSearch)).getText().toString();
        String src = ((EditText)findViewById(R.id.srcTxtSearch)).getText().toString();
        String dest = ((EditText)findViewById(R.id.destTxtSearch)).getText().toString();

        if(airlineName.isEmpty()){
            openDialog("Invalid Airline Name", String.format(invalidIp, "A non-empty String. eg: British Airways", "empty or null"));
            ((EditText)findViewById(R.id.airlineNameTxtIp)).setText("");
            return;
        }
        ArrayList<Airline> airlineArrayList = getAirlines();
        for(Airline airline: airlineArrayList){
            if(airline.getName().equalsIgnoreCase(airlineName)){
                ArrayList<Flight> filteredFlights = new ArrayList<>();
                if(!src.isEmpty()){
                    for(Flight flight: airline.getFlights()){
                        if(flight.getSource().equalsIgnoreCase(src)){
                            filteredFlights.add(flight);
                        }
                    }
                }
                if(!dest.isEmpty()){
                    for(Flight flight: airline.getFlights()){
                        if(flight.getDestination().equalsIgnoreCase(src)){
                            filteredFlights.add(flight);
                        }
                    }
                }
                if(filteredFlights.size()!=0){
                    airline.getFlights().removeAll(airline.getFlights());
                    airline.getFlights().addAll(filteredFlights);
                }
            }
        }

    }

    private ArrayList<Airline> getAirlines() {
        InternalStorageHelper internalStorageHelper = new InternalStorageHelper(getString(R.string.internalStoragePath), this);
        ArrayList<Airline> airlineArrayList = internalStorageHelper.readFromFile();
        return airlineArrayList;
    }

    private void openDialog(String title, String message) {
        ErrorDialog dialog = new ErrorDialog();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show(getSupportFragmentManager(), "Error dialog");
    }
    private void changeStatusBarColor(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }
    private void menuHandler() {
        MenuHandler menuHandler = new MenuHandler(this);
        menuHandler.buttonActions();
    }

    private void closeKeyboard(){
        RelativeLayout mainLayout;
        mainLayout = findViewById(R.id.parentLayoutSearch);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
    }
}