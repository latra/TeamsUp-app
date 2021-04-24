package com.example.teamsup;

import androidx.annotation.Nullable;
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
            System.out.println("-----------------------------------------" + data.getStringExtra("usuario"));

            usuario.setText(data.getStringExtra("usuario"));
            direccion.setText(data.getStringExtra("direccion"));
            mail.setText(data.getStringExtra("mail"));
        }else{
            direccion.setText("error");
        }
    }

    public void createEvent(View view) {
        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }
}