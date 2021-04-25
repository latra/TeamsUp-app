package com.example.teamsup.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamsup.R;
import com.example.teamsup.models.EventBOModel;
import com.example.teamsup.models.EventModel;
import com.example.teamsup.models.UserModel;
import com.example.teamsup.utils.ConstantsUtils;
import com.example.teamsup.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class EventInfo extends Fragment {

    TextView titulo;
    TextView direccion;
    TextView maxParticipantes;
    ImageView sportType;
    EventBOModel event;

    public EventInfo() {
        new EventBOModel(new EventModel());
    }
    public EventInfo(EventBOModel event) {
        this.event = event;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        titulo = view.findViewById(R.id.nombreEvento);
        direccion = view.findViewById(R.id.direccionEvento);
        maxParticipantes = view.findViewById(R.id.maxParticipantes);
        sportType = view.findViewById(R.id.tematicaImg);
        if (event != null && event.eventModel != null) {
            titulo.setText(event.eventModel.title);
            direccion.setText(event.eventModel.direction);
            maxParticipantes.setText(String.valueOf(event.eventModel.maxParticipants));
            sportType.setImageDrawable(ContextCompat.getDrawable(view.getContext(),
                    ConstantsUtils.recoverEventImage(event.eventModel.sport_type)));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_event_info, container, false);
    }
}