package com.example.teamsup.models;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

public class EventBOModel {
    public EventModel eventModel;
    public float distance;
    public EventBOModel(){
        new EventBOModel(new EventModel(), 0);
    }
    public EventBOModel(EventModel eventModel){
        new EventBOModel(eventModel, 0);
    }
    public EventBOModel(EventModel eventModel, float distance){
        this.eventModel = eventModel;
        this.distance = distance;
    }

    public void calculateDistance(GeoPoint userGeopoing) {

        if (userGeopoing != null && eventModel.coordinates != null) {
            Location loc1 = new Location("");
            loc1.setLatitude(eventModel.coordinates.getLatitude());
            loc1.setLongitude(eventModel.coordinates.getLongitude());

            Location loc2 = new Location("");
            loc2.setLatitude(userGeopoing.getLatitude());
            loc2.setLongitude(userGeopoing.getLongitude());

            this.distance = loc1.distanceTo(loc2) / 1000;
        }
    }
}
