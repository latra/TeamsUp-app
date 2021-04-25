package com.example.teamsup.fragments;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.teamsup.R;
import com.example.teamsup.activities.TemplateActivity;
import com.example.teamsup.utils.ConstantsUtils;
import com.example.teamsup.models.EventModel;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateEvent extends Fragment implements View.OnClickListener {

    private Spinner spinner;
    EditText input_date;
    EditText input_hour;
    EditText input_direction;
    EditText input_name;
    EditText input_maxparticipants;
    Geocoder geocoder;
    Address calculated_address;

    public CreateEvent() {

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        geocoder = new Geocoder(getContext());

        spinner = view.findViewById(R.id.spinner);
        ArrayList<String> tematicas = setTematicas();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, tematicas);
        input_date = view.findViewById(R.id.input_fecha);
        input_hour = view.findViewById(R.id.input_hora);
        input_name = view.findViewById(R.id.input_name);
        input_direction = view.findViewById(R.id.input_direccion);
        input_direction.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    proposeDirections();
                }
            }
        });
        input_maxparticipants = view.findViewById(R.id.num_particpantes);
        spinner.setAdapter(adp);
        input_date.setOnClickListener(this);
        Button createButton = view.findViewById(R.id.btnRegistrar);
        createButton.setOnClickListener((vw) -> {
            EventModel event = new EventModel();
            event.title = input_name.getText().toString();
            try {
                event.date = new SimpleDateFormat("dd / MM / yyyy hh:mm").parse(input_date.getText().toString() + " " + input_hour.getText().toString());

                event.direction = input_direction.getText().toString();
                event.maxParticipants = Integer.parseInt(input_maxparticipants.getText().toString());
                event.sport_type = ConstantsUtils.recoverEventType(spinner.getSelectedItemPosition());
                event.owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (calculated_address != null) {
                    event.direction = calculated_address.getAddressLine(0);
                    event.coordinates = new GeoPoint(calculated_address.getLatitude(), calculated_address.getLongitude());
                    event.hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(calculated_address.getLatitude(), calculated_address.getLongitude()));

                }
                event.createOrUpdateEvent();
                ((TemplateActivity) getActivity()).updateFragment(new Home());
            } catch (ParseException e) {
                event.date = new Date();
            }
        });
        MaterialTextView cancelButton = view.findViewById(R.id.tvCancelar);
        cancelButton.setOnClickListener((vw) -> {
            ((TemplateActivity) getActivity()).updateFragment(new Home());
        });
    }

    private void proposeDirections() {
        try {
            List<Address> proposedDirections = geocoder.getFromLocationName(input_direction.getText().toString(), 1);
            for (Address address : proposedDirections) {
                calculated_address = address;
                Log.println(Log.INFO, "TAG", address.getAddressLine(0));
                input_direction.setText(address.getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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