package edu.pdx.cs410J.kgujral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AirlineListAdapter extends BaseAdapter {

    ArrayList<Airline> airlineArrayList;
    List<Airline> airlines;
    LayoutInflater layoutInflater;

    public AirlineListAdapter(@NonNull Context context, ArrayList<Airline> userArrayList) {
        layoutInflater = LayoutInflater.from(context);
        this.airlineArrayList = userArrayList;
        airlines = new ArrayList<>();
        airlines.addAll(airlineArrayList);
    }

    @Override
    public int getCount(){
        return airlines.size();
    }

    @Override
    public Airline getItem(int i){
        return airlines.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Airline airline = getItem(position);
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.airline_list_item, parent, false);
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

    public void filter(String query){
        airlines.clear();
        if(query.isEmpty()){
            airlines.addAll(airlineArrayList);
        }
        else{
            for(Airline air: airlineArrayList){
                if(air.getName().toLowerCase().contains(query.toLowerCase())){
                    airlines.add(air);
                }
            }
        }

        notifyDataSetChanged();
    }
}
