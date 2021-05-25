package com.example.teamsup.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamsup.R;
import com.example.teamsup.models.UserModel;
import com.example.teamsup.utils.ConstantsUtils;
import com.example.teamsup.utils.FirebaseUtils;
import com.example.teamsup.utils.UserDataManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EditProfileActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;

    EditText usuario;
    EditText direccion;
    EditText mail;

    Button btnGuardar;
    Button btnCambiarFoto;

    SharedPreferences sharedpreferences;

    ImageView profile_photo;
    Uri uri;

    CheckBox checkbox_rugby;
    CheckBox checkbox_badminton;
    CheckBox checkbox_baseball;
    CheckBox checkbox_basketball;
    CheckBox checkbox_bowling;
    CheckBox checkbox_boxing;
    CheckBox checkbox_football;
    CheckBox checkbox_hockey;
    CheckBox checkbox_pingpong;
    CheckBox checkbox_volleyball;
    CheckBox checkbox_other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        usuario = findViewById(R.id.usuario_edit);
        direccion = findViewById(R.id.edit_direction);
        mail = findViewById(R.id.edit_mail);

        profile_photo = findViewById(R.id.photo_profile);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnCambiarFoto = findViewById(R.id.cambiarFoto);

        usuario.setText(sharedpreferences.getString(ConstantsUtils.KEY_USERNAME, ""));
        direccion.setText(sharedpreferences.getString(ConstantsUtils.KEY_DIRECTION, ""));
        mail.setText(sharedpreferences.getString(ConstantsUtils.KEY_EMAIL, ""));


        initializeCheckBox();
        UserDataManager dataManager = new UserDataManager(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel = new UserModel();
                userModel.email = mail.getText().toString();
                userModel.direction = direccion.getText().toString();
                userModel.username = direccion.getText().toString();
                userModel.eventTypesPreferences = new ArrayList<>();
                if(checkbox_rugby.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_FOOTBALL);
                if(checkbox_badminton.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_BADMINTON);
                if(checkbox_baseball.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_BASEBALL);
                if(checkbox_basketball.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_BASKET);
                if(checkbox_bowling.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_BOWLING);
                if(checkbox_boxing.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_BOXING);
                if(checkbox_football.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_FOOTBALL);
                if(checkbox_hockey.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_HOCKEY);
                if(checkbox_pingpong.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_PINGPONG);
                if(checkbox_volleyball.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_VOLEY);
                if(checkbox_other.isChecked())  userModel.eventTypesPreferences.add(ConstantsUtils.TYPE_OTHER);

                userModel.imgProfile = profile_photo;
                userModel.imgStorageUri = uri;


                FirebaseUtils.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String userModelId = documentSnapshot.toObject(UserModel.class).databaseId;
                        if (userModel != null) {
                            userModel.databaseId = userModelId;
                            userModel.save();
                        }
                    }
                });
                dataManager.ReadUserData(userModel);
                finish();
            }
        });

        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, 0);
            }
        });
    }

    private void initializeCheckBox() {
        checkbox_rugby = findViewById(R.id.checkbox_rugby);
        checkbox_badminton = findViewById(R.id.checkbox_badminton);
        checkbox_baseball = findViewById(R.id.checkbox_baseball);
        checkbox_basketball = findViewById(R.id.checkbox_basketball);
        checkbox_bowling = findViewById(R.id.checkbox_bowling);
        checkbox_boxing = findViewById(R.id.checkbox_boxing);
        checkbox_football = findViewById(R.id.checkbox_football);
        checkbox_hockey = findViewById(R.id.checkbox_hockey);
        checkbox_pingpong = findViewById(R.id.checkbox_pingpong);
        checkbox_volleyball = findViewById(R.id.checkbox_volleyball);
        checkbox_other = findViewById(R.id.checkbox_other);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                ImageView img = findViewById(R.id.photo_profile);
                uri = data.getData();
                try {
                    InputStream str = getContentResolver().openInputStream(uri);
                    Bitmap bit = BitmapFactory.decodeStream(str);
                    img.setImageBitmap(bit);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}