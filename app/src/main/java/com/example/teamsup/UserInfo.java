package com.example.teamsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserInfo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);



    }
    public void createEvent(View view) {
        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }
}