package edu.pdx.cs410J.kgujral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class HelpContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_content);

        // Hide the Action Bar
        getSupportActionBar().hide();

        // Change the color of the status bar
        changeStatusBarColor();

        // get topic info
        HelpTopic helpTopic = getHelpTopic();

        // set topic info
        setHelpTopicInfo(helpTopic);

        // call menu handler
        menuHandler();

    }

    private void setHelpTopicInfo(HelpTopic helpTopic) {
        TextView helpTopicTxt = this.findViewById(R.id.help_topic_txt);
        TextView helpTopicContentTxt = this.findViewById(R.id.help_topic_content_txt);
        // set scrollbar for textview
        helpTopicContentTxt.setMovementMethod(new ScrollingMovementMethod());

        helpTopicTxt.setText(helpTopic.getTopic());
        helpTopicContentTxt.setText(helpTopic.getContent());
    }

    private HelpTopic getHelpTopic() {
        if(getIntent().getExtras() != null) {
            HelpTopic helpTopic = getIntent().getSerializableExtra("helpTopic", HelpTopic.class);
            return helpTopic;
        }
        return null;
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