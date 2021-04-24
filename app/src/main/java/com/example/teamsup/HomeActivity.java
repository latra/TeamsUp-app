package com.example.teamsup;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private NearEventItemAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView nearEventsListView = (ListView) findViewById(
                R.id.near_event_list);
        ArrayList<EventItem> nearEvents = new ArrayList<>();
        nearEvents.add(new EventItem());
        nearEvents.add(new EventItem());
        nearEvents.add(new EventItem());
        madapter = new NearEventItemAdapter(this, nearEvents);
        nearEventsListView.setAdapter(madapter);
    }
}
