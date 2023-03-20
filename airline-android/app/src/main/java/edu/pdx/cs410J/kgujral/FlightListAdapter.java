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

public class FlightListAdapter extends ArrayAdapter<Flight> {

    public FlightListAdapter(@NonNull Context context, ArrayList<Flight> userArrayList) {
        super(context, R.layout.list_item, userArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Flight flight = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView flightNum = convertView.findViewById(R.id.flightNumberDisplay);
        TextView flightSrc = convertView.findViewById(R.id.srcDisplay);
        TextView flightDest = convertView.findViewById(R.id.destDisplay);
        TextView flightDepart = convertView.findViewById(R.id.departureDisplay);
        TextView flightArrive = convertView.findViewById(R.id.arrivalDisplay);

        flightNum.setText(flight.getNumber());
        flightSrc.setText(flight.getSource());
        flightDest.setText(flight.getDestination());
        flightDepart.setText(flight.getDepartureString());
        flightArrive.setText(flight.getArrivalString());

        return convertView;
    }
}
