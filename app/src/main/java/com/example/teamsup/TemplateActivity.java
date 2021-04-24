package com.example.teamsup;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
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

    public  void updateFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place, fragment);
        ft.commit();
    }
}