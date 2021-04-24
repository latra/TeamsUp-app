package com.example.teamsup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class CreateEvent extends AppCompatActivity implements View.OnClickListener{

    private Spinner spinner;
    EditText input_date;
    EditText input_hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        spinner = findViewById(R.id.spinner);
        ArrayList<String> tematicas = setTematicas();

        ArrayAdapter<String> adp = new ArrayAdapter<>(CreateEvent.this, android.R.layout.simple_spinner_dropdown_item, tematicas);
        input_date = findViewById(R.id.input_fecha);
        input_hour = findViewById(R.id.input_hora);

        spinner.setAdapter(adp);
        input_date.setOnClickListener(this);
    }

    private ArrayList<String> setTematicas() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Introducir temática");
        list.add("Futbol");
        list.add("Baloncesto");
        list.add("Pádel");
        list.add("Tenis");
        list.add("Joquei");
        list.add("Bádminton");

        return list;
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.input_fecha) {
            showDatePickerDialog();
        }
    }
    private void showDatePickerDialog() {
        DatePickerFragment datePicker = new DatePickerFragment(input_date);
        datePicker.show(getSupportFragmentManager(), "datePicker");

    }
}