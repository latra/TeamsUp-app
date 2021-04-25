package com.example.teamsup.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.teamsup.BuildConfig;
import com.example.teamsup.adapters.EventListAdapter;
import com.example.teamsup.R;
import com.example.teamsup.activities.TemplateActivity;
import com.example.teamsup.models.EventBOModel;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.utils.FirebaseUtils;
import com.example.teamsup.utils.UserDataManager;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    GeoPoint userGeoPoint;
    private EventListAdapter nearadapter;
    private EventListAdapter recommendadapter;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        new Thread( () ->getData(view)).run();
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
        ListView nearEventsListView = getView().findViewById(
                R.id.near_event_list);
        ListView recommendedEventsListView = getView().findViewById(
                R.id.recommended_event_list);

        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            // Se recupera cada minuto para evitar sacurar a llamadas
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, (location) -> {
                if (location != null) {
                    userGeoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                    Log.i("LATITUDE", String.valueOf(location.getLatitude()));
                    Log.i("LONGITUDE", String.valueOf(location.getLongitude()));
                }
                FirebaseUtils.getRecommendedEvents(userDataManager.getTypePreferences())
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<EventBOModel> events = new ArrayList<>();

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        EventBOModel event = new EventBOModel();
                                        event.eventModel = document.toObject(EventModel.class);
                                        event.calculateDistance(userGeoPoint);
                                        events.add(event);
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
                                    ArrayList<EventBOModel> events = new ArrayList<>();

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        EventBOModel event = new EventBOModel();
                                        event.eventModel = document.toObject(EventModel.class);
                                        event.calculateDistance(userGeoPoint);
                                        events.add(event);
                                        Log.d("TAG", document.getId() + " => " + document.getData());
                                    }
                                    nearadapter = new EventListAdapter(view.getContext(), events);
                                    nearEventsListView.setAdapter(nearadapter);
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            });



        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            ShowGrantPermissionsInfo();
        } else {
            // You can directly ask for the permission.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
        nearEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((TemplateActivity)getActivity()).updateFragment(new EventInfo());
            }
        });
        recommendedEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TemplateActivity)getActivity()).updateFragment(new EventInfo(id));

            }
        });

    }

    public void ShowGrantPermissionsInfo() {
        showSnackbar(R.string.permission_denied_explanation,
                R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Build intent that displays the App settings screen.
                        Intent intent = new Intent();
                        intent.setAction(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",
                                BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                getView().findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }
}