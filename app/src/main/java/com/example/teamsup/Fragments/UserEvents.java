package com.example.teamsup.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teamsup.adapters.EventListAdapter;
import com.example.teamsup.R;

import java.util.ArrayList;

public class UserEvents extends Fragment {

    private TextView mTextView;
    private EventListAdapter adapterEvents;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListView myEvents = getView().findViewById(
                R.id.myEvents);

        ArrayList<EventItem> events = new ArrayList<>();
        events.add(new EventItem());
        events.add(new EventItem());
        events.add(new EventItem());
        adapterEvents = new EventListAdapter(getView().getContext(), events);
        myEvents.setAdapter(adapterEvents);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_user_events, container, false);
    }
}