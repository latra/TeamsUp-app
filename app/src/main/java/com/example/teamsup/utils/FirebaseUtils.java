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

import java.util.ArrayList;

public class FirebaseUtils {

    public static ArrayList<EventModel> recoverRecommendedEvents() {
        ArrayList<EventModel> eventModels = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return eventModels;

    }

    public static Task<DocumentSnapshot> getUser(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("user").document(id).get();
    }

    public static void createOrUploadEvent(EventModel event) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (event.databaseId != null && event.databaseId.length() > 0)
            db.collection("events").document(event.databaseId).
                    set(event)
                    .addOnSuccessListener((a) ->
                            Log.d("FB UPLOAD", "DocumentSnapshot added with ID: " + event.databaseId)
                    )
                    .addOnFailureListener((e) ->
                            Log.w("FB UPLOAD", "Error adding document", e)

                    );
        else
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

    public static void createOrUpdateUser(UserModel user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (user.databaseId != null && user.databaseId.length() > 0)
            db.collection("users").document(user.databaseId).
                    set(user)
                    .addOnSuccessListener((a) ->
                            Log.d("FB UPLOAD", "DocumentSnapshot added with ID: " + user.databaseId)
                    )
                    .addOnFailureListener((e) ->
                            Log.w("FB UPLOAD", "Error adding document", e)

                    );
        else
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("FB UPLOAD", "DocumentSnapshot added with ID: " + documentReference.getId());
                            user.databaseId = documentReference.getId();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FB UPLOAD", "Error adding document", e);
                        }
                    });
    }
}
