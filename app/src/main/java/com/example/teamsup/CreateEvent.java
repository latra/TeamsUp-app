package com.example.teamsup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.teamsup.models.EventModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateEvent extends Fragment implements View.OnClickListener{

    private Spinner spinner;
    EditText input_date;
    EditText input_hour;
    EditText input_direction;
    EditText input_name;
    EditText input_maxparticipants;
    public CreateEvent(){

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        spinner = view.findViewById(R.id.spinner);
        ArrayList<String> tematicas = setTematicas();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, tematicas);
        input_date = view.findViewById(R.id.input_fecha);
        input_hour = view.findViewById(R.id.input_hora);
        input_name = view.findViewById(R.id.input_name);
        input_direction = view.findViewById(R.id.input_direccion);
        input_maxparticipants = view.findViewById(R.id.num_particpantes);
        spinner.setAdapter(adp);
        input_date.setOnClickListener(this);
        Button createButton = view.findViewById(R.id.btnRegistrar);
        createButton.setOnClickListener((vw) -> {
            EventModel event = new EventModel();
            event.title = input_name.getText().toString();
            try {
                event.date = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(input_date.getText().toString() + " " + input_hour.getText().toString());
            } catch (ParseException e) {
                event.date = new Date();
            }
            event.direction = input_direction.getText().toString();
            event.maxParticipants = Integer.parseInt(input_maxparticipants.getText().toString());
            event.sport_type = Utils.recoverEventType(spinner.getSelectedItemPosition());
            Utils.createOrUploadEvent(event);
            ((TemplateActivity)getActivity()).updateFragment(new Home());
        });
    }

    private ArrayList<String> setTematicas() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Introducir temática");
        list.add("Futbol");
        list.add("Baloncesto");
        list.add("Pádel");
        list.add("Tenis");
        list.add("Hockey");
        list.add("Bádminton");

        return list;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.input_fecha) {
            showDatePickerDialog();
        }
    }
    private void showDatePickerDialog() {
        DatePickerFragment datePicker = new DatePickerFragment(input_date);
        datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event, container, false);
    }
}