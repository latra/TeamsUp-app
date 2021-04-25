

package com.example.teamsup.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.teamsup.R;
import com.example.teamsup.activities.RegisterActivity;
import com.example.teamsup.activities.TemplateActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button btnEntrar;
    EditText input_email;
    EditText input_password;

    CheckBox remember;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_login_app);

        mAuth = FirebaseAuth.getInstance();

        btnEntrar = findViewById(R.id.btnEntrar);
        input_email = findViewById(R.id.input_mail);
        input_password = findViewById(R.id.input_pswd);
        remember = findViewById(R.id.remember);


        if (mAuth.getCurrentUser() != null) login();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    login();
                                } else {
                                    Toast.makeText(getApplication(), "Email y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

        }
    }

    public void registrate(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login() {
        Intent intent = new Intent(this, TemplateActivity.class);
        startActivity(intent);

    }




}

