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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListView myEvents = getView().findViewById(
                R.id.myEvents);

        FirebaseUtils.getUserEvents(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<EventBOModel> events = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventBOModel event = new EventBOModel(document.toObject(EventModel.class));
                                event.calculateDistance(userGeopoint);
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


    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            // Se recupera cada minuto para evitar sacurar a llamadas
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, (location) -> {
                if (location != null) {
                    int userLatitude = (int) (location.getLatitude() * 1E6);
                    int userLongitude = (int) (location.getLongitude() * 1E6);
                    userGeopoint = new GeoPoint(userLatitude, userLongitude);
                    Log.i("LATITUDE", String.valueOf(location.getLongitude()));
                    Log.i("LONGITUDE", String.valueOf(location.getLongitude()));
                }
            });



        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            ShowGrantPermissionsInfo();
        } else {
            // You can directly ask for the permission.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
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