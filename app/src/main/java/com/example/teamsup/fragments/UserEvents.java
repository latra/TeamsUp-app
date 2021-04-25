package com.example.teamsup.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teamsup.adapters.EventListAdapter;
import com.example.teamsup.R;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.models.UserModel;
import com.example.teamsup.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserEvents extends Fragment {

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
                            ArrayList<EventModel> events = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                events.add(document.toObject(EventModel.class));
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
}