package com.example.teamsup.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.teamsup.adapters.EventListAdapter;
import com.example.teamsup.R;
import com.example.teamsup.models.EventBOModel;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.utils.FirebaseUtils;
import com.example.teamsup.utils.UserDataManager;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ArrayList<EventBOModel> RecommendedEvents;
    ArrayList<EventBOModel> randomEvents;
    private EventListAdapter nearadapter;
    private EventListAdapter recommendadapter;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    ListView nearEventsListView;
    ListView recommendedEventsListView;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        randomEvents = new ArrayList<>();
        RecommendedEvents = new ArrayList<>();
        getData(view);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getUserLocation();

        FloatingActionButton floatingActionButton = view.findViewById(R.id.newEventButton);
        floatingActionButton.setOnClickListener((vw) -> ((TemplateActivity) getActivity()).updateFragment(new CreateEvent()));
    }

    private void getUserLocation() {
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            try {
                mFusedLocationClient.getLastLocation()
                        .addOnCompleteListener((Executor) this, new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    mLastLocation = task.getResult();
                                } else {
                                    Log.w("TAG", "getLastLocation:exception", task.getException());
                                }
                            }
                        });
            } catch (Exception exception) {

            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void getData(View view) {
        UserDataManager userDataManager = new UserDataManager(getActivity());
        nearEventsListView = getView().findViewById(
                R.id.near_event_list);
        recommendedEventsListView = getView().findViewById(
                R.id.recommended_event_list);

        FirebaseUtils.getRecommendedEvents(userDataManager.getTypePreferences())
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventBOModel event = new EventBOModel();
                                event.eventModel = document.toObject(EventModel.class);
                                RecommendedEvents.add(event);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            recommendadapter = new EventListAdapter(view.getContext(), RecommendedEvents);
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

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventBOModel event = new EventBOModel();
                                event.eventModel = document.toObject(EventModel.class);
                                randomEvents.add(event);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            nearadapter = new EventListAdapter(view.getContext(), randomEvents);
                            nearEventsListView.setAdapter(nearadapter);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void updateData(GeoPoint userGeoPoint) {
        if (randomEvents != null && RecommendedEvents != null) {
            for (EventBOModel event : randomEvents)
                event.calculateDistance(userGeoPoint);

            nearEventsListView.setAdapter(nearadapter);

            for (EventBOModel event : RecommendedEvents)
                event.calculateDistance(userGeoPoint);
            recommendedEventsListView.setAdapter(recommendadapter);
        }
    }



}
