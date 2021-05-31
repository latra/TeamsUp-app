package com.example.teamsup.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamsup.fragments.DatePickerFragment;
import com.example.teamsup.R;
import com.example.teamsup.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    EditText input_date;
    TextView politica_privacidad;
    EditText nombre_input;
    EditText mail_input;
    EditText input_direction;
    EditText paswd_input;
    EditText paswd_input2;
    CheckBox checkBox;

    Button registerBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        input_direction = findViewById(R.id.input_direccion);

        input_date = findViewById(R.id.input_edad);
        checkBox = findViewById(R.id.policy_checkbox);
        registerBtn = findViewById(R.id.btnRegistrar);
        nombre_input = findViewById(R.id.input_name);
        mail_input = findViewById(R.id.input_mail2);
        paswd_input = findViewById(R.id.input_pswd2);
        paswd_input2 = findViewById(R.id.input_pswd2_1);
        input_date.setOnClickListener(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mail_input.getText().toString();
                String password = paswd_input.getText().toString();
                String password2 = paswd_input2.getText().toString();
                String nombre = nombre_input.getText().toString();
                String direction = nombre_input.getText().toString();
                String birthDate = nombre_input.getText().toString();

                if(!emptyFields(mail, password, nombre, direction, birthDate)){
                    if(correctPassword(password, password2)){
                        if(checkBox.isChecked()){
                            mAuth.createUserWithEmailAndPassword(mail, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                UserModel userModel = new UserModel();
                                                userModel.databaseId = task.getResult().getUser().getUid();
                                                userModel.direction = direction;
                                                userModel.name = nombre;
                                                userModel.email = mail;
                                                try {
                                                    userModel.birthDate = new SimpleDateFormat("dd / MM / yyyy").parse(input_date.getText().toString());
                                                } catch (ParseException ignored) {}
                                                userModel.save();
                                                Toast.makeText(getApplication(), "Usuari creat correctament!", Toast.LENGTH_SHORT).show();
                                                finish();

                                            } else {
                                                // If sign in fails, display a message to the user.Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplication(), "Email ja registat!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(getApplication(), "Debes aceptar la política de privacidad.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplication(), "Las conrtaseñas no coinciden.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplication(), "Todos los campos deben de estar llenos.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean correctPassword(String password, String password2) {
        return password.equals(password2);
    }

    private boolean emptyFields(String mail, String password, String nombre, String direction, String birthDate) {
        return TextUtils.isEmpty(mail) || TextUtils.isEmpty(password) || TextUtils.isEmpty(nombre) || TextUtils.isEmpty(direction) || TextUtils.isEmpty(birthDate);
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
        Toast.makeText(RegisterActivity.this, "Registro cancelado. ", Toast.LENGTH_LONG).show();
        finish();
    }

    public void showPolicity(View view) {
        Snackbar mySnackbar = Snackbar.make(view, R.string.politica_priv, Snackbar.LENGTH_LONG);

        mySnackbar.show();

    }

    public void returnLogin() {
        if(checkBox.isChecked()){
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(RegisterActivity.this, "Debes aceptar la política de privadiad ", Toast.LENGTH_LONG).show();
        }
    }
}
