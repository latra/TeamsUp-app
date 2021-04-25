package com.example.teamsup.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teamsup.R;
import com.example.teamsup.models.EventBOModel;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.models.UserModel;
import com.example.teamsup.utils.ConstantsUtils;
import com.example.teamsup.utils.FirebaseUtils;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NearEventsMap extends Fragment implements OnMapReadyCallback {
    public static final String MyPREFERENCES = "MyPrefs";

    List<EventBOModel> nearEvents;
    GoogleMap googleMap;
    SharedPreferences sharedpreferences;
    GeoPoint lastUserLocation;

    public NearEventsMap() {
    }

    ;

    public void loadNearEvents(GeoPoint userLocation) {
        lastUserLocation = userLocation;
        final double radiusInM = sharedpreferences.getInt(ConstantsUtils.KEY_RADAR, ConstantsUtils.DEFAULT_RADAR) * 1000;
// depending on overlap, but in most cases there are 4.
        GeoLocation geoLocation = new GeoLocation(userLocation.getLatitude(), userLocation.getLongitude());
        List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(geoLocation, radiusInM);
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        for (GeoQueryBounds b : bounds) {
            Query q = FirebaseUtils.getNearEvents(b);

            tasks.add(q.get());
        }
        // Collect all the query results together into a single list
        Tasks.whenAllComplete(tasks)
                .addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Task<?>>> t) {
                        nearEvents = new ArrayList<>();
                        for (Task<QuerySnapshot> task : tasks) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                EventBOModel event = new EventBOModel();
                                EventModel eventModel = doc.toObject(EventModel.class);
                                event.eventModel = eventModel;
                                event.calculateDistance(userLocation);
                                nearEvents.add(event);
                            }
                        }
                        updateEvents();
                    }
                });
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_near_events_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mapLayaut, mapFragment)
                .commit();

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (nearEvents != null) {
            updateEvents();
        }
    }

    public void updateEvents() {
        if (googleMap != null && getContext() != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastUserLocation.getLatitude(), lastUserLocation.getLongitude()),
                    10f));


            googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(lastUserLocation.getLatitude(), lastUserLocation.getLongitude()))
                    .radius(sharedpreferences.getInt(ConstantsUtils.KEY_RADAR, ConstantsUtils.DEFAULT_RADAR) * 1000)
                    .strokeColor(Color.RED)
                    .fillColor(Color.argb(1, 255, 255, 255)));
            for (EventBOModel event : nearEvents) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(event.eventModel.coordinates.getLatitude(), event.eventModel.coordinates.getLongitude()))
                        .title(String.format("%s - %s", getString(ConstantsUtils.recoverEventName(event.eventModel.sport_type)),
                                event.eventModel.title)));
            }
        }
    }
}