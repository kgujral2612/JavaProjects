package edu.pdx.cs410J.kgujral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Help extends AppCompatActivity {

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