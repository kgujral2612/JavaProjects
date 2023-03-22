package edu.pdx.cs410J.kgujral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import edu.pdx.cs410J.kgujral.databinding.ActivityMainBinding;

public class Help extends AppCompatActivity {

    private ListView listView;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Hide the Action Bar
        getSupportActionBar().hide();

        // Change the color of the status bar
        changeStatusBarColor();

        // call menu handler
        menuHandler();

        listView = findViewById(R.id.help_topic_list);

        // initialize custom adapter
        createCustomAdapter();
    }

    private void createCustomAdapter() {

        HelpTopic[] helpTopics = HelpTopicHelper.getHelpTopics();
        HelpListAdapter helpListAdapter = new HelpListAdapter(this,  helpTopics);
        listView.setAdapter(helpListAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            HelpTopic helpTopic = helpListAdapter.getItemAtPosition(position);
            Intent intent = new Intent(this, HelpContent.class);
            intent.putExtra("helpTopic", helpTopic);
            startActivity(intent);
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