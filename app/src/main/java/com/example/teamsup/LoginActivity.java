

package com.example.teamsup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

<<<<<<< HEAD
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
=======
>>>>>>> e172a113bb63b052af958cc75a8fb3c15d098754
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

<<<<<<< HEAD
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


=======
>>>>>>> e172a113bb63b052af958cc75a8fb3c15d098754
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


       // if (mAuth.getCurrentUser() != null) login();

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
        if(currentUser != null){

        }
    }

    public void registrate(View view) {
        Intent intent = new Intent(this, Register_activity.class);
        startActivity(intent);
    }

<<<<<<< HEAD
    public void login() {
        Intent intent = new Intent(this, UserInfo.class);
=======
    public void login(View view) {
        Intent intent = new Intent(this, TemplateActivity.class);
>>>>>>> e172a113bb63b052af958cc75a8fb3c15d098754
        startActivity(intent);
    }

}

