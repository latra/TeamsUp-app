package com.example.teamsup.models;

import java.util.ArrayList;
import java.util.Date;

public class UserModel {
    public String databaseId;
    public String email;
    public String username;
    public String name;
    public Date birthDate;
    public ArrayList<Integer> eventTypesPreferences;
    public String direction;
    public UserModel(){
        new UserModel("", "", "", "", new Date(), new ArrayList<Integer>(), "");
    }
    public UserModel(String databaseId, String email, String username, String name, Date birthDate, ArrayList<Integer> eventTypesPreferences, String direction){
        this.databaseId = databaseId;
        this.email = email;
        this.username = username;
        this.name = name;
        this.birthDate = birthDate;
        this.eventTypesPreferences = eventTypesPreferences;
        this.direction = direction;
    }
}
