package com.example.teamsup.fragments;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamsup.R;
import com.example.teamsup.activities.EditProfileActivity;
import com.example.teamsup.activities.LoginActivity;
import com.example.teamsup.activities.TemplateActivity;
import com.example.teamsup.utils.ConstantsUtils;
import com.example.teamsup.utils.UserDataManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.Set;


public class UserInfo extends Fragment {

    public UserInfo() {
    }

    public static final String MyPREFERENCES = "MyPrefs";

    ImageView editbutton;
    TextView usuario;
    TextView direccion;
    TextView mail;
    Button logoutButton;
    SharedPreferences sharedpreferences;
    TextView distanceUserRadar;
    SeekBar distanceUserBar;
    Button misEventos;

    public void setValues() {
        direccion.setText(sharedpreferences.getString(ConstantsUtils.KEY_DIRECTION, ""));
        usuario.setText(sharedpreferences.getString(ConstantsUtils.KEY_USERNAME, ""));
        mail.setText(sharedpreferences.getString(ConstantsUtils.KEY_EMAIL, ""));
        distanceUserRadar.setText(String.format("%s KM", sharedpreferences.getInt(ConstantsUtils.KEY_RADAR, ConstantsUtils.DEFAULT_RADAR)));
        distanceUserBar.setProgress(sharedpreferences.getInt(ConstantsUtils.KEY_RADAR, ConstantsUtils.DEFAULT_RADAR));
        distanceUserBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub

                distanceUserRadar.setText(String.format("%s KM", String.valueOf(progress)));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(ConstantsUtils.KEY_RADAR, seekBar.getProgress());
                editor.commit();
            }
        });
        deletePreferences();
        setPreferences();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editbutton = view.findViewById(R.id.edit_button);
        distanceUserRadar = view.findViewById(R.id.user_radar_distance);
        distanceUserBar = view.findViewById(R.id.user_radar_bar);
        direccion = view.findViewById(R.id.direccionUser);
        usuario = view.findViewById(R.id.nombreUsuario);
        mail = view.findViewById(R.id.mailUser);
        misEventos = view.findViewById(R.id.button);
        logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener((v -> {
            FirebaseAuth.getInstance().signOut();
            FragmentManager fm = getActivity().getSupportFragmentManager();

            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }

            Intent i = new Intent(getActivity(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        }));

        setValues();

        editbutton.setOnClickListener((v) -> {
            Intent i = new Intent(getActivity(), EditProfileActivity.class);
            // Lo comento porque no veo que sentido tiene hacerlo aquí :/
            //                i.putExtra("usuario", (String) usuario.getText());
            //                i.putExtra("direccion", (String) direccion.getText());
            //                i.putExtra("mail", (String) mail.getText());
            startActivityForResult(i, 2);
        });
        Button createEvent = view.findViewById(R.id.crear_evento);
        createEvent.setOnClickListener((v) -> createEvent());

        misEventos.setOnClickListener((v) -> ((TemplateActivity) getActivity()).updateFragment(new UserEvents()));
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == getActivity().RESULT_OK && data != null) {
            usuario.setText(data.getStringExtra("usuario"));
            direccion.setText(data.getStringExtra("direccion"));
            mail.setText(data.getStringExtra("mail"));
            deletePreferences();
            setPreferences();
        }

    }

    @Override
    public void onResume() {
        setValues();
        super.onResume();
    }

    private void deletePreferences() {
        ImageView im1 = getView().findViewById(R.id.ImagePref1);
        im1.setImageResource(android.R.color.transparent);
        ImageView im2 = getView().findViewById(R.id.ImagePref2);
        im2.setImageResource(android.R.color.transparent);
        ImageView im3 = getView().findViewById(R.id.ImagePref3);
        im3.setImageResource(android.R.color.transparent);
        ImageView im4 = getView().findViewById(R.id.ImagePref4);
        im4.setImageResource(android.R.color.transparent);
        ImageView im5 = getView().findViewById(R.id.ImagePref5);
        im5.setImageResource(android.R.color.transparent);
        ImageView im6 = getView().findViewById(R.id.ImagePref6);
        im6.setImageResource(android.R.color.transparent);
        ImageView im7 = getView().findViewById(R.id.ImagePref7);
        im7.setImageResource(android.R.color.transparent);
        ImageView im8 = getView().findViewById(R.id.ImagePref8);
        im8.setImageResource(android.R.color.transparent);
        ImageView im9 = getView().findViewById(R.id.ImagePref9);
        im9.setImageResource(android.R.color.transparent);
        ImageView im10 = getView().findViewById(R.id.ImagePref10);
        im10.setImageResource(android.R.color.transparent);
        ImageView im11 = getView().findViewById(R.id.ImagePref11);
        im11.setImageResource(android.R.color.transparent);
/*        int id_img;
        for (int i=0; i<11; i++){
            id_img = getResources().getIdentifier("ImagePref" + i, "id", getPackageName());
            ImageView img = findViewById(id_img);
            img.setImageResource(android.R.color.transparent);
        }*/
    }

    private void setPreferences() {
        Set<String> deportes = sharedpreferences.getStringSet(ConstantsUtils.KEY_SPORT, Collections.singleton(""));
        int img_pos = 1;

        for (String deporte : deportes) {
            int id_img = getResources().getIdentifier("ImagePref" + img_pos, "id", getActivity().getPackageName());
            int id_drawable = ConstantsUtils.recoverEventImage(Integer.parseInt(deporte));

            ImageView img = getView().findViewById(id_img);
            img.setImageResource(id_drawable);
            img_pos++;
        }


    }

    public void createEvent() {
        ((TemplateActivity) getActivity()).updateFragment(new CreateEvent());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }
}