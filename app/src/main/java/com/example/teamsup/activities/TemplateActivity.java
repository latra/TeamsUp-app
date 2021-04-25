package com.example.teamsup.activities;

import android.os.Bundle;

import com.example.teamsup.fragments.Home;
import com.example.teamsup.R;
import com.example.teamsup.fragments.UserInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.widget.ImageView;

public class TemplateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        updateFragment(new Home());

        ImageView userIcon = findViewById(R.id.UserIco);
        ImageView homeIcon = findViewById(R.id.HomeIco);
        userIcon.setOnClickListener((view) -> updateFragment(new UserInfo()));
        homeIcon.setOnClickListener((view) -> updateFragment(new Home()));
    }

    public void updateFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place, fragment);
        ft.commit();
    }
}