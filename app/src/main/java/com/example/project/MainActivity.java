package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button admin, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("BookFlix");

        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);

    }

    public void goToAdminPanel(View view) {
        Intent intent = new Intent(MainActivity.this, AdminLogin.class);
        startActivity(intent);
        return;
    }


    public void goToUserPanel(View view) {
        Intent intent = new Intent(MainActivity.this, UserLogin.class);
        startActivity(intent);
        return;
    }

}