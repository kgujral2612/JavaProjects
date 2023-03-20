package edu.pdx.cs410J.kgujral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.pdx.cs410J.kgujral.databinding.ActivityMainBinding;

public class DisplayAirline extends AppCompatActivity {

    private ListView listView;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_display_airline);

        // Hide the Action Bar
        getSupportActionBar().hide();

        // Change the color of the status bar
        changeStatusBarColor();

        // get the airline which needs to be displayed
        Airline airline = getAirline();

        // set airline name on the display page
        setAirlineName(airline);

        listView = findViewById(R.id.flight_list);

        // initialize custom adapter
        createCustomAdapter(airline);

    }

    private void createCustomAdapter(Airline airline) {
        FlightListAdapter flightListAdapter = new FlightListAdapter(this, (ArrayList<Flight>) airline.getFlights());
        listView.setAdapter(flightListAdapter);
        listView.setClickable(false);
    }

    private void setAirlineName(Airline airline){
        TextView airlineName = this.findViewById(R.id.airlineNameDisplay);
        airlineName.setText(airline.getName());
    }
    private Airline getAirline() {
        if(getIntent().getExtras() != null) {
            Airline airline = getIntent().getSerializableExtra("airline", Airline.class);
            return airline;
        }
        return null;
    }

    private void changeStatusBarColor(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }
}