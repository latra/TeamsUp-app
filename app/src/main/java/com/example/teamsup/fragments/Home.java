package com.example.teamsup.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.teamsup.adapters.EventListAdapter;
import com.example.teamsup.R;
import com.example.teamsup.activities.TemplateActivity;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.utils.FirebaseUtils;
import com.example.teamsup.utils.UserDataManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        UserDataManager userDataManager = new UserDataManager(getActivity());
        FirebaseUtils.getRecommendedEvents(userDataManager.getTypePreferences())
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<EventModel> events = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                events.add(document.toObject(EventModel.class));
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            recommendadapter = new EventListAdapter(view.getContext(), events);
                            recommendedEventsListView.setAdapter(recommendadapter);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        FirebaseUtils.getAllEvents()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<EventModel> events = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                events.add(document.toObject(EventModel.class));
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            nearadapter = new EventListAdapter(view.getContext(), events);
                            nearEventsListView.setAdapter(nearadapter);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
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