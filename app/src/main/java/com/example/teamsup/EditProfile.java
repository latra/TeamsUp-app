package com.example.teamsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

public class EditProfile extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;

    EditText usuario;
    EditText direccion;
    EditText mail;

    Button btnGuardar;

    SharedPreferences sharedpreferences;

    CheckBox checkbox_rugby;
    CheckBox checkbox_badminton;
    CheckBox checkbox_baseball;
    CheckBox checkbox_basketball;
    CheckBox checkbox_bowling;
    CheckBox checkbox_boxing;
    CheckBox checkbox_football;
    CheckBox checkbox_hockey;
    CheckBox checkbox_pingpong;
    CheckBox checkbox_volleyball;
    CheckBox checkbox_other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Bundle b = this.getIntent().getExtras();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        usuario = findViewById(R.id.usuario_edit);
        direccion = findViewById(R.id.edit_direction);
        mail = findViewById(R.id.edit_mail);

        btnGuardar = findViewById(R.id.btnGuardar);

        usuario.setText(b.getString("usuario"));
        direccion.setText(b.getString("direccion"));
        mail.setText(b.getString("mail"));

        initializeCheckBox();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Set<String> deportes = new HashSet<>();
                Intent i = new Intent();
                i.putExtra("usuario", String.valueOf(usuario.getText()));
                i.putExtra("direccion", String.valueOf(direccion.getText()));
                i.putExtra("mail", String.valueOf(mail.getText()));
                setResult(RESULT_OK, i);

                SharedPreferences.Editor editor = sharedpreferences.edit();


                if(checkbox_rugby.isChecked()) deportes.add("american_football");
                if(checkbox_badminton.isChecked()) deportes.add("badminton");
                if(checkbox_baseball.isChecked()) deportes.add("baseball");
                if(checkbox_basketball.isChecked()) deportes.add("basketball");
                if(checkbox_bowling.isChecked()) deportes.add("bowling");
                if(checkbox_boxing.isChecked()) deportes.add("boxing");
                if(checkbox_football.isChecked()) deportes.add("football");
                if(checkbox_hockey.isChecked()) deportes.add("hockey");
                if(checkbox_pingpong.isChecked()) deportes.add("ping_pong");
                if(checkbox_volleyball.isChecked()) deportes.add("volleyball");
                if(checkbox_other.isChecked()) deportes.add("other");

                editor.putStringSet("Deportes", deportes);
                editor.commit();

                finish();
            }
        });
    }

    private void initializeCheckBox() {
        checkbox_rugby = findViewById(R.id.checkbox_rugby);
        checkbox_badminton = findViewById(R.id.checkbox_badminton);
        checkbox_baseball = findViewById(R.id.checkbox_baseball);
        checkbox_basketball = findViewById(R.id.checkbox_basketball);
        checkbox_bowling = findViewById(R.id.checkbox_bowling);
        checkbox_boxing = findViewById(R.id.checkbox_boxing);
        checkbox_football = findViewById(R.id.checkbox_football);
        checkbox_hockey = findViewById(R.id.checkbox_hockey);
        checkbox_pingpong = findViewById(R.id.checkbox_pingpong);
        checkbox_volleyball = findViewById(R.id.checkbox_volleyball);
        checkbox_other = findViewById(R.id.checkbox_other);
    }


}