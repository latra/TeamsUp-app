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

import com.example.teamsup.fragments.EventItem;
import com.example.teamsup.R;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.utils.ConstantsUtils;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<EventModel> {
         public EventListAdapter(@NonNull Context context, ArrayList<EventModel> eventModels) {
        super(context, 0, eventModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        EventModel detectedActivity = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.fragment_event_item, parent, false);
        }

        // Find the UI widgets.
        TextView activityType = (TextView) view.findViewById(R.id.event_type);
        TextView activityTitle = (TextView) view.findViewById(
                R.id.event_title);
        ImageView activityLogo = (ImageView) view.findViewById(R.id.image_sportico);

        // Populate widgets with values.
        if (detectedActivity != null) {
            activityTitle.setText(detectedActivity.title);

            activityType.setText(ConstantsUtils.recoverEventName(detectedActivity.sport_type));
            activityLogo.setImageDrawable(ContextCompat.getDrawable(getContext(),
                    ConstantsUtils.recoverEventImage(detectedActivity.sport_type)));
        }
        return view;
    }

}
