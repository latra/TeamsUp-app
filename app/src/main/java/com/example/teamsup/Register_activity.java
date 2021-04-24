package com.example.teamsup;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Register_activity extends AppCompatActivity implements View.OnClickListener {

    EditText input_date;
    TextView politica_privacidad;
    EditText mail;
    CheckBox checkBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        input_date = findViewById(R.id.input_edad);
        checkBox = findViewById(R.id.policy_checkbox);

        input_date.setOnClickListener(this);

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

    public void returnLogin(View view) {
        if(checkBox.isChecked()){
            Intent i = new Intent(Register_activity.this, LoginActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(Register_activity.this, "Debes aceptar la política de privadiad ", Toast.LENGTH_LONG).show();
        }
    }
}
