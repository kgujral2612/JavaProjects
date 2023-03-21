package edu.pdx.cs410J.kgujral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.pdx.cs410J.kgujral.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private SearchView searchView;
    private ListView listView;
    ActivityMainBinding binding;
    AirlineListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        // Hide the Action Bar
        getSupportActionBar().hide();

        // Change the color of the status bar
        changeStatusBarColor();

        // call menu handler
        menuHandler();

        // Add intent to the button
        addBtn = findViewById(R.id.add_button);
        addBtn.setOnClickListener(view -> openAddAirline());

        // To do: must search by airline/flight src/dest/number
        // Search View
        searchView = findViewById(R.id.search_bar);

        // List View
        listView = findViewById(R.id.list_items);

        // custom adapter
        createCustomAdapter();

        // search query
        customSearchView();
    }

    private void customSearchView() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.this.listAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MainActivity.this.listAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void menuHandler() {
        MenuHandler menuHandler = new MenuHandler(this);
        menuHandler.buttonActions();
    }

    private void createCustomAdapter() {
        listAdapter = new AirlineListAdapter(MainActivity.this, getAirlines());
        listView.setAdapter(listAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Airline airline = listAdapter.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, DisplayAirline.class);
            intent.putExtra("airline", airline);
            startActivity(intent);
        });
    }
    private void openAddAirline() {
        Intent intent = new Intent(this, AddInfo.class);
        startActivity(intent);
    }
    private ArrayList<Airline> getAirlines(){

        InternalStorageHelper internalStorageHelper = new InternalStorageHelper(getString(R.string.internalStoragePath), this);
        ArrayList<Airline> airlines = internalStorageHelper.readFromFile();

        if(airlines == null)
            airlines = new ArrayList<>();

        return airlines;
    }

    private void changeStatusBarColor(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }
}