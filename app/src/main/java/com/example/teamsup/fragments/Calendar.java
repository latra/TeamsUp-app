package com.example.teamsup.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teamsup.R;
import com.example.teamsup.adapters.EventListAdapter;
import com.example.teamsup.models.EventBOModel;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.models.UserModel;
import com.example.teamsup.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class Calendar extends Fragment {

    MCalendarView calendar;
    ArrayList<EventBOModel> events;
    ListView eventsList;
    HashMap<String, String> eventID;

    TextView event_type;
    TextView date_event;
    TextView eventDescription;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        events = new ArrayList<>();
        calendar = (MCalendarView) view.findViewById(R.id.calendar);

        eventID = new HashMap<>();
        event_type = view.findViewById(R.id.event_type);
        date_event = view.findViewById(R.id.date_event);
        eventDescription = view.findViewById(R.id.eventDescription);

        event_type.setVisibility(view.INVISIBLE);
        date_event.setVisibility(view.INVISIBLE);
        eventDescription.setVisibility(view.INVISIBLE);



        calendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                String dateString = date.getYear() + "/" + date.getMonthString() + "/" + date.getDayString();
                String id = eventID.get(dateString);
                System.out.println(id + "/" + dateString);
                if(id!=null){
                    FirebaseUtils.getEvent(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            EventBOModel event = new EventBOModel();
                            event.eventModel = documentSnapshot.toObject(EventModel.class);
                            event_type.setText(event.eventModel.title);
                            event_type.setVisibility(view.VISIBLE);
                            date_event.setText(dateString);
                            date_event.setVisibility(view.VISIBLE);
                            eventDescription.setText(event.eventModel.description);
                            eventDescription.setVisibility(view.VISIBLE);
                           // events.add(event);

                            //eventadapter = new EventListAdapter(view.getContext(), events);
                            //eventsList.setAdapter(eventadapter);
                        }
                    });
                }else{
                    event_type.setVisibility(view.INVISIBLE);
                    date_event.setVisibility(view.INVISIBLE);
                    eventDescription.setVisibility(view.INVISIBLE);
                }

            }
        });

        FirebaseUtils.getAllEvents()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventBOModel event = new EventBOModel();
                                event.eventModel = document.toObject(EventModel.class);
                                Date date = event.eventModel.date;
                                String databaseID = document.getId();
                                markDate(date, databaseID);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private void markDate(Date date, String databaseId) {
        int year, day;
        String month;
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateAsString = df.format(date);

        month = dateAsString.substring(0, 2);
        day = Integer.parseInt(dateAsString.substring(3, 5));
        year = Integer.parseInt(dateAsString.substring(6, 10));


        calendar.markDate(year, Integer.parseInt(month),day);
        eventID.put(year + "/" + month + "/" + day, databaseId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_calendar, container, false);
    }
}
