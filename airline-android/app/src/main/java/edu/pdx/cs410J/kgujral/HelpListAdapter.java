package edu.pdx.cs410J.kgujral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HelpListAdapter extends ArrayAdapter<HelpTopic> {
    public HelpListAdapter(@NonNull Context context, HelpTopic[] helpTopics) {
        super(context, R.layout.help_menu_item, helpTopics);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        HelpTopic helpTopic = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.help_menu_item, parent, false);
        }

        TextView helpTopicTxt = convertView.findViewById(R.id.help_topic);

        helpTopicTxt.setText(helpTopic.getTopic());

        return convertView;
    }
}
