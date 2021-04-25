package com.example.teamsup.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.teamsup.models.EventModel;
import com.example.teamsup.models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirebaseUtils {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static Task<QuerySnapshot> getUserEvents(String userId) {
        return db.collection("events")
                .whereEqualTo("owner", userId)
                .get();
    }
    public static Task<QuerySnapshot> getRecommendedEvents(ArrayList<Integer> userPreferences) {
        if (userPreferences.size() > 0)
            return  db.collection("events")
                .whereIn("sport_type", userPreferences)
                .get();
        else
            return  getAllEvents();

    }
    public static Task<QuerySnapshot> getAllEvents() {
        return  db.collection("events")
                .get();

    }

    public static Task<DocumentSnapshot> getEvent(String id) {
        return db.collection("events").document(id).get();
    }

    public static Task<DocumentSnapshot> getUser(String id) {
        return db.collection("users").document(id).get();
    }

    public static void createEvent(EventModel event) {
        db.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FB UPLOAD", "DocumentSnapshot added with ID: " + documentReference.getId());
                        event.databaseId = documentReference.getId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FB UPLOAD", "Error adding document", e);
                    }
                });
    }

    public static void updateEvent(EventModel event) {
        db.collection("events").document(event.databaseId).
                set(event)
                .addOnSuccessListener((a) ->
                        Log.d("FB UPLOAD", "DocumentSnapshot added with ID: " + event.databaseId)
                )
                .addOnFailureListener((e) ->
                        Log.w("FB UPLOAD", "Error adding document", e)

                );


    }

    public static void createUser(UserModel user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.databaseId).
                set(user)
                .addOnSuccessListener((a) ->
                        Log.d("FB UPLOAD", "DocumentSnapshot added with ID: " + user.databaseId)
                )
                .addOnFailureListener((e) ->
                        Log.w("FB UPLOAD", "Error adding document", e)

                );
    }


}
