package com.example.teamsup.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.teamsup.BuildConfig;
import com.example.teamsup.adapters.EventListAdapter;
import com.example.teamsup.fragments.Home;
import com.example.teamsup.R;
import com.example.teamsup.fragments.NearEventsMap;
import com.example.teamsup.fragments.UserInfo;
import com.example.teamsup.models.EventBOModel;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.utils.FirebaseUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class TemplateActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    Fragment activeFragment;
    GeoPoint userGeoPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        updateFragment(activeFragment = new Home());

        ImageView userIcon = findViewById(R.id.UserIco);
        ImageView mapIcon = findViewById(R.id.mapIcon);
        ImageView homeIcon = findViewById(R.id.HomeIco);
        userIcon.setOnClickListener((view) -> updateFragment(new UserInfo()));
        homeIcon.setOnClickListener((view) -> updateFragment(new Home()));
        mapIcon.setOnClickListener((view) -> updateFragment(new NearEventsMap()));


        new Thread(() -> getUserCoordinates()).run();

        public void updateFragment(Fragment fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.addToBackStack("back");
            ft.commit();

        }
        public void getUserCoordinates(){
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                // Se recupera cada minuto para evitar sacurar a llamadas
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, (location) -> {
                    if (location != null) {
                        userGeoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                        if (activeFragment != null && activeFragment.getClass() == Home.class){
                            ((Home) activeFragment).updateData(userGeoPoint);
                        }

                        if (activeFragment != null && activeFragment.getClass() == NearEventsMap.class){
                            ((NearEventsMap) activeFragment).loadNearEvents(userGeoPoint);
                        }                    Log.i("LATITUDE", String.valueOf(location.getLatitude()));
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
        public void updateFragment(Fragment fragment) {
            activeFragment = fragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
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
                    findViewById(android.R.id.content),
                    getString(mainTextStringId),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(actionStringId), listener).show();
        }


    }