package com.example.teamsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;

    EditText usuario;
    EditText direccion;
    EditText mail;

    Button btnGuardar;
    SharedPreferences sharedpreferences;
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


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.putExtra("usuario", String.valueOf(usuario.getText()));
                i.putExtra("direccion", String.valueOf(direccion.getText()));
                i.putExtra("mail", String.valueOf(mail.getText()));
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }


}