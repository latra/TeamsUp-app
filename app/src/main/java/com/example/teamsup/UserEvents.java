package com.example.teamsup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class UserEvents extends Fragment {

    private TextView mTextView;
    private EventListAdapter nearadapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListView myEvents = getView().findViewById(
                R.id.myEvents);

        ArrayList<EventItem> nearEvents = new ArrayList<>();
        nearEvents.add(new EventItem());
        nearEvents.add(new EventItem());
        nearEvents.add(new EventItem());
        nearadapter = new EventListAdapter(getView().getContext(), nearEvents);
        myEvents.setAdapter(nearadapter);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_user_events, container, false);
    }
}