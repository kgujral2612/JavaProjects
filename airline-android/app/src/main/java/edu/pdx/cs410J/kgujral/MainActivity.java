package edu.pdx.cs410J.kgujral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.pdx.cs410J.kgujral.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private SearchView searchView;
    private ListView listView;

    String[] nameList = {"KG", "Harry", "Sheena", "Shanshu", "Fury"};

    ArrayAdapter<String> arrayAdapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        // Hide the Action Bar
        getSupportActionBar().hide();

        // Change the color of the status bar
        changeStatusBarColor();

        // Add intent to the button
        addBtn = findViewById(R.id.add_button);
        addBtn.setOnClickListener(view -> openAddAirline());

        // To do: must search by airline/flight src/number
        // Search View
        searchView = findViewById(R.id.search_bar);

        // List View
        listView = findViewById(R.id.list_items);

        createCustomAdapter();

        /*
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MainActivity.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });*/
    }

    private void createCustomAdapter() {
        ListAdapter listAdapter = new ListAdapter(MainActivity.this, getAirlines());
        listView.setAdapter(listAdapter);
        listView.setClickable(true);
        //listView.setOnItemClickListener((parent, view, position, id) -> {
          //  Toast.makeText(MainActivity.this, "You clicked" + listAdapter.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
        //});
    }

    private void changeStatusBarColor(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }
    private void openAddAirline() {
        Intent intent = new Intent(this, AddInfo.class);
        startActivity(intent);
    }
    private ArrayList<Airline> getAirlines(){
        String[] airlineNames = {"British Airways", "Indigo Airlines", "Kingfisher Airlines", "Qatar Airways", "Air India", "Singapore Airlines"};
        Flight f1 = new Flight(1234, "SFO", "PDX", DateHelper.stringToDate("03/03/2020 6:00 am"), DateHelper.stringToDate("03/03/2020 9:00 am"));
        Flight f2 = new Flight(4321, "BER", "PDX", DateHelper.stringToDate("03/03/2020 6:00 am"), DateHelper.stringToDate("03/03/2020 9:00 am"));
        ArrayList<Airline> airlines = new ArrayList<>();
        for(int i=0; i<airlineNames.length; i++){
            Airline airline = new Airline(airlineNames[i]);
            if(i%2==0)
                airline.addFlight(f1);
            else
                airline.addFlight(f2);
            airlines.add(airline);
        }
        return airlines;
    }
}