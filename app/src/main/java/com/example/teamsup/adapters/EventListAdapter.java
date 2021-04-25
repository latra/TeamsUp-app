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

import com.example.teamsup.R;
import com.example.teamsup.models.EventBOModel;
import com.example.teamsup.utils.ConstantsUtils;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<EventBOModel> {
         public EventListAdapter(@NonNull Context context, ArrayList<EventBOModel> eventModels) {
        super(context, 0, eventModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        EventBOModel detectedActivity = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.fragment_event_item, parent, false);
        }

        // Find the UI widgets.
        TextView activityType = (TextView) view.findViewById(R.id.event_type);
        TextView activityTitle = (TextView) view.findViewById(
                R.id.event_title);
        ImageView activityLogo = (ImageView) view.findViewById(R.id.image_sportico);
        TextView distanceText = view.findViewById(R.id.eventDistance);
        // Populate widgets with values.
        if (detectedActivity != null) {
            activityTitle.setText(detectedActivity.eventModel.title);
            distanceText.setText(String.format("%.1f KM", detectedActivity.distance));

            activityType.setText(ConstantsUtils.recoverEventName(detectedActivity.eventModel.sport_type));
            activityLogo.setImageDrawable(ContextCompat.getDrawable(getContext(),
                    ConstantsUtils.recoverEventImage(detectedActivity.eventModel.sport_type)));
        }
        return view;
    }

}
