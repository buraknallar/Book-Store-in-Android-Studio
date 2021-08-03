package com.example.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Admin_User extends AppCompatActivity {

    Button admin, user;
    ImageView bookflix;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user);
        setTitle("BookFlix");

        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);

        //bookflix = findViewById(R.id.bookflix);

        ImageView iv = (ImageView)findViewById(R.id.bookflix);
        iv.setImageResource(R.drawable.bookflix);


    }



}
