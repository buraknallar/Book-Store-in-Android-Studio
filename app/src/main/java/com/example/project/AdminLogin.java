package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity {

    Button login, back;
    EditText adminName, adminPass;

    String name = "admin";
    String password = "1234";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        login = findViewById(R.id.login);
        back = findViewById(R.id.back);

        adminName = findViewById(R.id.adminName);
        adminPass = findViewById(R.id.adminPass);

        setTitle("BookFlix");



    }

    public void goToAdminPanel(View view) {

        String control1 = adminName.getText().toString();
        String control2 = adminPass.getText().toString();

        if(control1.equals("admin") && control2.equals("1234")){
            Intent intent = new Intent(AdminLogin.this, AdminPanel.class);
            startActivity(intent);
            return;
        }
        else{
            Toast.makeText(getApplicationContext(), "Wrong ID or Password", Toast.LENGTH_LONG).show();
        }


    }

    public void goToMainPanel(View view) {
        Intent intent = new Intent(AdminLogin.this, MainActivity.class);
        startActivity(intent);
        return;
    }

}
