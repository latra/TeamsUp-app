package com.example.teamsup;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register_activity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    EditText input_date;
    TextView politica_privacidad;
    EditText nombre_input;
    EditText mail_input;
    EditText paswd_input;
    CheckBox checkBox;

    Button registerBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        input_date = findViewById(R.id.input_edad);
        checkBox = findViewById(R.id.policy_checkbox);
        registerBtn = findViewById(R.id.btnRegistrar);
        nombre_input = findViewById(R.id.input_name);
        mail_input = findViewById(R.id.input_mail2);
        paswd_input = findViewById(R.id.input_pswd2);
        input_date.setOnClickListener(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mail_input.getText().toString();
                String password = paswd_input.getText().toString();
                String nombre = nombre_input.getText().toString();

                mAuth.createUserWithEmailAndPassword(mail, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();


                                    Toast.makeText(getApplication(), "Usuari creat correctament!", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplication(), "Email ja registat!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.input_edad) {
            showDatePickerDialog();
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment datePicker = new DatePickerFragment(input_date);
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    public void cancel(View view) {
        Toast.makeText(Register_activity.this, "Registro cancelado. ", Toast.LENGTH_LONG).show();
        finish();
    }

    public void showPolicity(View view) {
        Snackbar mySnackbar = Snackbar.make(view, R.string.politica_priv, Snackbar.LENGTH_LONG);

        mySnackbar.show();

    }

    public void returnLogin() {
        if(checkBox.isChecked()){
            Intent i = new Intent(Register_activity.this, LoginActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(Register_activity.this, "Debes aceptar la política de privadiad ", Toast.LENGTH_LONG).show();
        }
    }
}
