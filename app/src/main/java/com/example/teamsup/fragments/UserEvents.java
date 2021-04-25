package com.example.teamsup.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.teamsup.BuildConfig;
import com.example.teamsup.adapters.EventListAdapter;
import com.example.teamsup.R;
import com.example.teamsup.models.EventBOModel;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.models.UserModel;
import com.example.teamsup.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserEvents extends Fragment {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private GeoPoint userGeopoint;
    private TextView mTextView;
    private EventListAdapter adapterEvents;
    ArrayList<EventBOModel> events;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListView myEvents = getView().findViewById(
                R.id.myEvents);
        events = new ArrayList<>();

        FirebaseUtils.getUserEvents(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventBOModel event = new EventBOModel();
                                EventModel eventModel = document.toObject(EventModel.class);
                                event.eventModel = eventModel;
                                events.add(event);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            adapterEvents = new EventListAdapter(view.getContext(), events);
                            myEvents.setAdapter(adapterEvents);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_user_events, container, false);
    }
}