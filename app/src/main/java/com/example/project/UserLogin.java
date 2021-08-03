package com.example.project;

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

public class UserLogin extends AppCompatActivity {

    EditText name, pass;
    Button login, back, signup;
    UserDatabaseManager userManager;

    SQLiteDatabase database;

    String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        setTitle("BookFlix");


        name = findViewById(R.id.userName);
        pass = findViewById(R.id.userPass);

        login = findViewById(R.id.userLogin);


        File file = getApplication().getFilesDir();
        path = file + "/USERS_1"; // path of the db

        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        userManager = new UserDatabaseManager(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = name.getText().toString();
                String p = pass.getText().toString();

                //check authorized user or not

                if (userManager.check(u, p) == true) {
                    Intent intent =new Intent(UserLogin.this, UserPanel.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_LONG).show();
                }
            }
        });

    }





    public void goToUserPanel(View view) {
        Intent intent = new Intent(UserLogin.this, UserPanel.class);
        startActivity(intent);
        return;

    }

    public void goToMainPanel(View view) {
        Intent intent = new Intent(UserLogin.this, MainActivity.class);
        startActivity(intent);
        return;
    }

    public void goToUserSignup(View view) {
        Intent intent = new Intent(UserLogin.this, UserSignup.class);
        startActivity(intent);
        return;
    }




}
