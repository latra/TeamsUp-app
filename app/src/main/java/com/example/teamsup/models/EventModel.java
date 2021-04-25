package com.example.teamsup.models;

import com.example.teamsup.utils.ConstantsUtils;

import java.util.ArrayList;
import java.util.Date;

public class EventModel {
    public String databaseId;
    public String title;
    public String description;
    public int sport_type;
    public Date date;
    public String direction;
    public ArrayList<UserModel> assistants;
    public int maxParticipants;
    public EventModel() {
        new EventModel("", "", "", ConstantsUtils.TYPE_OTHER, new Date(), "", new ArrayList<>(), 0);
    }
    public EventModel(String databaseId, String title, String description, int sport_type, Date date, String direction, ArrayList<UserModel> assistants, int maxParticipants) {
        this.title = title;
        this.databaseId = databaseId;
        this.description = description;
        this.sport_type = sport_type;
        this.date = date;
        this.direction = direction;
        this.assistants = assistants;
        this.maxParticipants = maxParticipants;
    }
}
