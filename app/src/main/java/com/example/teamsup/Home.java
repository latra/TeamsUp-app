package com.example.teamsup;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EventListAdapter nearadapter;
    private EventListAdapter recommendadapter;

    public Home() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView nearEventsListView = getView().findViewById(
                R.id.near_event_list);
        ListView recommendedEventsListView = getView().findViewById(
                R.id.recommended_event_list);
        ArrayList<EventItem> nearEvents = new ArrayList<>();
        nearEvents.add(new EventItem());
        nearEvents.add(new EventItem());
        nearEvents.add(new EventItem());
        nearadapter = new EventListAdapter(getView().getContext(), nearEvents);
        nearEventsListView.setAdapter(nearadapter);
        recommendadapter = new EventListAdapter(getView().getContext(), nearEvents);
        recommendedEventsListView.setAdapter(recommendadapter);


        FloatingActionButton floatingActionButton = view.findViewById(R.id.newEventButton);
        floatingActionButton.setOnClickListener((vw) -> ((TemplateActivity)getActivity()).updateFragment(new CreateEvent()));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}