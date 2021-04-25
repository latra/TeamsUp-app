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
import com.example.teamsup.fragments.Home;
import com.example.teamsup.R;
import com.example.teamsup.fragments.NearEventsMap;
import com.example.teamsup.fragments.UserInfo;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

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

public class TemplateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        updateFragment(new Home());

        ImageView userIcon = findViewById(R.id.UserIco);
        ImageView mapIcon = findViewById(R.id.mapIcon);
        ImageView homeIcon = findViewById(R.id.HomeIco);
        userIcon.setOnClickListener((view) -> updateFragment(new UserInfo()));
        homeIcon.setOnClickListener((view) -> updateFragment(new Home()));
        mapIcon.setOnClickListener((view) -> {
            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_place, mapFragment)
                    .commit();
        });


    }

    public void updateFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place, fragment);
        ft.commit();
    }



}