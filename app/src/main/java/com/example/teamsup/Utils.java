package com.example.teamsup;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.teamsup.models.EventModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Utils {
    public static final int TYPE_OTHER=0;
    public static final int TYPE_FOOTBALL=1;
    public static final int TYPE_BASKET=2;
    public static final int TYPE_PADEL=3;
    public static final int TYPE_TENIS=4;
    public static final int TYPE_HOCKEY=5;
    public static final int TYPE_BADMINTON=6;


    public static ArrayList<EventModel> recoverRecommendedEvents() {
        ArrayList<EventModel> eventModels = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return  eventModels;

    }

    public static void createOrUploadEvent(EventModel event){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FB UPLOAD", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FB UPLOAD", "Error adding document", e);
                    }
                });

    }

    public static int recoverEventType(int eventPosition) {
        switch (eventPosition){
            case 1:
                return TYPE_FOOTBALL;
            case 2:
                return TYPE_BASKET;
            case 3:
                return TYPE_PADEL;
            case 4:
                return TYPE_TENIS;
            case 5:
                return TYPE_HOCKEY;
            case 6:
                return TYPE_BADMINTON;
            default:
                return TYPE_OTHER;
        }
    }
}
