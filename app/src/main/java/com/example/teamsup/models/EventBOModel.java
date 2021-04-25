package com.example.teamsup.models;

public class EventBOModel {
    public EventModel eventModel;
    public double distance;
    public EventBOModel(){
        new EventBOModel(new EventModel(), 0);
    }
    public EventBOModel(EventModel eventModel){
        new EventBOModel(eventModel, 0);
    }
    public EventBOModel(EventModel eventModel, double distance){
        this.eventModel = eventModel;
        this.distance = distance;
    }
}
