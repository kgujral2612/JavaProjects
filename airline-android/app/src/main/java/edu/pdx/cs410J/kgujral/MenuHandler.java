package edu.pdx.cs410J.kgujral;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuHandler {
    Context context;
    public MenuHandler(@NonNull Context context) {
        this.context = context;
    }

    public void buttonActions() {
        // On click button actions
        FloatingActionButton addBtn = ((Activity) this.context).findViewById(R.id.add_button);
        FloatingActionButton homeBtn = ((Activity) this.context).findViewById(R.id.home_button);
        FloatingActionButton helpBtn = ((Activity) this.context).findViewById(R.id.help_button);

        addBtn.setOnClickListener(view -> openScreen(AddInfo.class));
        homeBtn.setOnClickListener(view -> openScreen(MainActivity.class));
        helpBtn.setOnClickListener(view -> openScreen(Help.class));
    }

    private void openScreen(Class<?> screen) {
        if(this.context.getClass().getSimpleName().equals(screen.getSimpleName())){
            return;
        }
        Intent intent = new Intent(this.context, screen);
        this.context.startActivity(intent);
    }

}
