package com.example.teamsup.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teamsup.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventItem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "param1";
    private static final String DESCRIPTION = "param2";
    private static final String TYPE = "param3";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    public EventItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventItem.
     */
    // TODO: Rename and change types and number of parameters
    public static EventItem newInstance(String param1, String param2, String type) {
        EventItem fragment = new EventItem();
        Bundle args = new Bundle();
        args.putString(TITLE, param1);
        args.putString(DESCRIPTION, param2);
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TITLE);
            mParam2 = getArguments().getString(DESCRIPTION);
            mParam3 = getArguments().getString(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_item, container, false);
    }
}