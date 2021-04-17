package com.example.teamsup;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_login_app);

    }

    public void registrate(View view) {
        Toast.makeText(MainActivity.this, "Iniciando proceso de registro", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, Register_activity.class);
        startActivity(intent);
    }
}
