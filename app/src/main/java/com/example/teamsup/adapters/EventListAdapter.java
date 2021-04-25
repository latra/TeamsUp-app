package com.example.teamsup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.teamsup.Fragments.EventItem;
import com.example.teamsup.R;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<EventItem> {
         public EventListAdapter(@NonNull Context context, ArrayList<EventItem> eventItemArrayAdapter) {
        super(context, 0, eventItemArrayAdapter);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        EventItem detectedActivity = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.fragment_event_item, parent, false);
        }

        // Find the UI widgets.
        TextView activityName = (TextView) view.findViewById(R.id.event_type);
        TextView activityConfidenceLevel = (TextView) view.findViewById(
                R.id.event_title);
        ImageView activityImage = (ImageView) view.findViewById(R.id.image_sportico);

        // Populate widgets with values.
        if (detectedActivity != null) {
            activityName.setText("TEST ITEM");

            activityConfidenceLevel.setText("TEST TITLE OF ITEM");
            activityImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sportico_other));
        }
        return view;
    }

}
