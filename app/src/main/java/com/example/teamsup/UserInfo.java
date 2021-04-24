package com.example.teamsup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class UserInfo extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;

    ImageView editbutton;
    TextView usuario;
    TextView direccion;
    TextView mail;

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editbutton = findViewById(R.id.edit_button);
        direccion = findViewById(R.id.direccionUser);
        usuario = findViewById(R.id.nombreUsuario);
        mail = findViewById(R.id.mailUser);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserInfo.this, EditProfile.class);
                i.putExtra("usuario", (String) usuario.getText());
                i.putExtra("direccion", (String) direccion.getText());
                i.putExtra("mail", (String) mail.getText());
                startActivityForResult(i, 2);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK && data!=null){
            usuario.setText(data.getStringExtra("usuario"));
            direccion.setText(data.getStringExtra("direccion"));
            mail.setText(data.getStringExtra("mail"));
            deletePreferences();
            setPreferences();
        }

    }

    private void deletePreferences() {
        ImageView im1 = findViewById(R.id.ImagePref1);
        im1.setImageResource(android.R.color.transparent);
        ImageView im2 = findViewById(R.id.ImagePref2);
        im2.setImageResource(android.R.color.transparent);
        ImageView im3 = findViewById(R.id.ImagePref3);
        im3.setImageResource(android.R.color.transparent);
        ImageView im4= findViewById(R.id.ImagePref4);
        im4.setImageResource(android.R.color.transparent);
        ImageView im5 = findViewById(R.id.ImagePref5);
        im5.setImageResource(android.R.color.transparent);
        ImageView im6 = findViewById(R.id.ImagePref6);
        im6.setImageResource(android.R.color.transparent);
        ImageView im7 = findViewById(R.id.ImagePref7);
        im7.setImageResource(android.R.color.transparent);
        ImageView im8 = findViewById(R.id.ImagePref8);
        im8.setImageResource(android.R.color.transparent);
        ImageView im9 = findViewById(R.id.ImagePref9);
        im9.setImageResource(android.R.color.transparent);
        ImageView im10 = findViewById(R.id.ImagePref10);
        im10.setImageResource(android.R.color.transparent);
        ImageView im11 = findViewById(R.id.ImagePref11);
        im11.setImageResource(android.R.color.transparent);
/*        int id_img;
        for (int i=0; i<11; i++){
            id_img = getResources().getIdentifier("ImagePref" + i, "id", getPackageName());
            ImageView img = findViewById(id_img);
            img.setImageResource(android.R.color.transparent);
        }*/
    }

    private void setPreferences() {
        Set<String> deportes = sharedpreferences.getStringSet("Deportes", Collections.singleton(""));
        int img_pos = 1;

        for (String deporte : deportes) {
            int id_img = getResources().getIdentifier("ImagePref" + img_pos, "id", getPackageName());
            int id_drawable = getResources().getIdentifier("sportico_" + deporte, "drawable", getPackageName());

            ImageView img = findViewById(id_img);
            img.setImageResource(id_drawable);
            img_pos++;
        }


    }

    public void createEvent(View view) {
        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }
}