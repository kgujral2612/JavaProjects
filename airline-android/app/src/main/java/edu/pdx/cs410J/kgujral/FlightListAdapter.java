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

    public FlightListAdapter(@NonNull Context context, ArrayList<Flight> flights) {
        super(context, R.layout.flight_info_item, flights);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Flight flight = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.flight_info_item, parent, false);
        }

        TextView flightNum = convertView.findViewById(R.id.flightNumberDisplay);
        TextView flightSrc = convertView.findViewById(R.id.srcDisplay);
        TextView flightDest = convertView.findViewById(R.id.destDisplay);
        TextView flightDepart = convertView.findViewById(R.id.departureDisplay);
        TextView flightArrive = convertView.findViewById(R.id.arrivalDisplay);
        TextView flightDuration = convertView.findViewById(R.id.totalDurationDisplay);

        flightNum.setText(Integer.toString(flight.getNumber()));
        flightSrc.setText(PrettyHelper.prettyAirportName(flight.getSource()));
        flightDest.setText(PrettyHelper.prettyAirportName(flight.getDestination()));
        flightDepart.setText(PrettyHelper.prettyDate(flight.getDeparture()));
        flightArrive.setText(PrettyHelper.prettyDate(flight.getArrival()));
        flightDuration.setText(PrettyHelper.prettyDuration(flight.getArrival(), flight.getDeparture()));

        return convertView;
    }
}
