package com.example.teamsup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {

    EditText usuario;
    EditText direccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        usuario = findViewById(R.id.usuario_edit);
        direccion = findViewById(R.id.edit_direction);

        Bundle extras = getIntent().getExtras();

        direccion.setText(extras.getString("direccion"));
        usuario.setText(extras.getString("nombreUsuario"));
    }
}