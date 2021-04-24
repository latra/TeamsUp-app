

package com.example.teamsup;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_login_app);

    }

    public void registrate(View view) {
        Intent intent = new Intent(this, Register_activity.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this, TemplateActivity.class);
        startActivity(intent);
    }
}
