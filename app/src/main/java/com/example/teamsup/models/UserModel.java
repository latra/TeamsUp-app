package com.example.teamsup.models;

import android.net.Uri;
import android.widget.ImageView;

import com.example.teamsup.utils.FirebaseUtils;

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
    public ImageView imgProfile;
    public Uri imgStorageUri;
    public String token;
    public String NetworkPref;

    public UserModel(){
        new UserModel("", "", "", "", new Date(), new ArrayList<Integer>(), "", Uri.parse(""), "");
    }
    public UserModel(String databaseId, String email, String username, String name, Date birthDate, ArrayList<Integer> eventTypesPreferences, String direction, Uri imgStorageUri, String NetworkPref){
        this.databaseId = databaseId;
        this.email = email;
        this.username = username;
        this.name = name;
        this.birthDate = birthDate;
        this.eventTypesPreferences = eventTypesPreferences;
        this.direction = direction;
        this.imgStorageUri = imgStorageUri;
        this.NetworkPref = NetworkPref;
    }

    public void save() {
        FirebaseUtils.createUser(this);
    }
}
