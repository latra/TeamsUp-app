package com.example.teamsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfo extends AppCompatActivity {

    ImageView editbutton;
    TextView usuario;
    TextView direccion;
    TextView mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        editbutton = findViewById(R.id.edit_button);
        direccion = findViewById(R.id.direccionUser);
        usuario = findViewById(R.id.nombreUsuario);
        mail = findViewById(R.id.mailUser);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Intent i = new Intent(UserInfo.this, EditProfile.class);
                b.putString("direccion", (String) direccion.getText());
                b.putString("nombreUsuario", (String) usuario.getText());
                b.putString("mail", (String) mail.getText());
                i.putExtras(b);
                startActivity(i);
            }
        });


    }
    public void createEvent(View view) {
        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }
}