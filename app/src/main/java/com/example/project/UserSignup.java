package com.example.project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class UserSignup extends AppCompatActivity {

    EditText username, name, surname, email, password;
    Button signup;

    UserDatabaseManager userManager;
    SQLiteDatabase database;

    String path;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup);
        setTitle("BookFlix");

        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signup = findViewById(R.id.signup);

        File file = getApplication().getFilesDir();
        path = file + "/USERS_1"; // path of the db

        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        userManager = new UserDatabaseManager(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(v);
            }
        });

    }



    public void insert(View v) {
        String u = username.getText().toString().trim();
        String n = name.getText().toString().trim();
        String s = surname.getText().toString().trim();
        String e = email.getText().toString().trim();
        String p = password.getText().toString().trim();

        if (u.equals("") || n.equals("") || s.equals("") || e.equals("") || p.equals("")) {
            Toast.makeText(UserSignup.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        } else if (userManager.checkUsername(u) == true) {
            userManager.insert(u, n, s, e, p);
            Toast.makeText(UserSignup.this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UserSignup.this, "This username is already exists", Toast.LENGTH_SHORT).show();
        }


        username.setText("");
        name.setText("");
        surname.setText("");
        email.setText("");
        password.setText("");

    }

    public void signUp(View view) {
        Intent intent = new Intent(UserSignup.this, UserLogin.class);
        startActivity(intent);
        return;

    }

}
