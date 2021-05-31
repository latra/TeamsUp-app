package com.example.teamsup.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import com.example.teamsup.models.EventModel;
import com.example.teamsup.models.UserModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserDataManager {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;

    public UserDataManager(Activity activity) {
        sharedPreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void ReadUserData(UserModel model) {

        if (model != null) {
            Set<String> deportes = new HashSet<>();
            if (model.eventTypesPreferences != null) {
                for (int value : model.eventTypesPreferences)
                    deportes.add(String.valueOf(value));
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ConstantsUtils.KEY_DIRECTION, model.direction);
            editor.putString(ConstantsUtils.KEY_EMAIL, model.email);
            editor.putString(ConstantsUtils.KEY_USERNAME, model.username);
            editor.putStringSet(ConstantsUtils.KEY_SPORT, deportes);
          //  editor.putString(ConstantsUtils.KEY_IMG_PROFILE, String.valueOf(model.imgStorageUri));
            editor.commit();
        }
    }

    public ArrayList<Integer> getTypePreferences() {
        Set<String> hashSet = sharedPreferences.getStringSet(ConstantsUtils.KEY_SPORT, new HashSet<>());
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String element : hashSet) {
            arrayList.add(Integer.parseInt(element));
        }
        return arrayList;
    }

}
