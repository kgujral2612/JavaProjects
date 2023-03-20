package edu.pdx.cs410J.kgujral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter  extends ArrayAdapter<Airline> {


    public ListAdapter(@NonNull Context context, ArrayList<Airline> userArrayList) {
        super(context, R.layout.list_item, userArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Airline airline = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView airlineName = convertView.findViewById(R.id.airlineName);
        TextView flightCount = convertView.findViewById(R.id.flightCount);

        airlineName.setText(airline.getName());
        flightCount.setText(airline.getFlights().toArray().length + " flights");

        return convertView;
    }

    public Airline getItemAtPosition(int position){
        return getItem(position);
    }
}
