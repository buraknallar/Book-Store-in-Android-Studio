package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PaymentClass extends AppCompatActivity {

    //defining objects
    TextView amount, time, edtPass, phoneText;
    Button exit, submit, password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);
        setTitle("BookFlix");

        //initializing objects
        amount = findViewById(R.id.amount);
        time = findViewById(R.id.date);
        exit = findViewById(R.id.last_exit);
        submit = findViewById(R.id.submit);
        password = findViewById(R.id.password);

        edtPass = findViewById(R.id.edtPass);

        phoneText = findViewById(R.id.phoneText);

        //Intent and bundle objects
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //this values takes values from MainActivity.java with the key "TOTAL"
        int total = bundle.getInt("TOTAL");

        String phone = bundle.getString("PHONE");

        //taking system time
        //Date date = new Date();
        Date date = new Date();

        //taking values from other class
        bundle.putInt("TOTAL", total);
        bundle.putString("PHONE", phone);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK);


        //putting total payment to intent
        amount.setText("AMOUNT: " + total + "$");
        phoneText.setText("PERSONAL MESSAGE: +90 " + phone);
        //putting system time to intent
        time.setText("DATE: " + systemDate("yyyy/MM/dd H:mm:ss"));

        //submit buttons Listener which is in Intent and payment_layout.xml
        //this buttons finishes intent. Actually it does not have an duty like
        //send data to server etc. It is symbolic.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //exit buttons Listener which is in Intent and payment_layout.xml
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HELPER METHOD
                exit(v);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String pass = edtPass.getText().toString();
               if(pass.equals("")){
                   Toast.makeText(getApplicationContext(), "Enter the password!", Toast.LENGTH_SHORT).show();
               }
               else{
                   finish();
                   Toast.makeText(getApplicationContext(), "Purchase Successful!", Toast.LENGTH_LONG).show();
               }
            }
        });


        final TextView textView = (TextView) findViewById(R.id.countDown);

        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                textView.setText(""+String.format(" %d Sec",
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
                textView.setText("Time Finished! Click Button to Take New Password!");
                password.setEnabled(true);
            }
        }.start();

        password.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                password.setEnabled(false);
                new CountDownTimer(10000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textView.setText(""+String.format(" %d Seconds Left!",
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                                toMinutes(millisUntilFinished))));
                    }
                    public void onFinish() {
                        textView.setText("Time Finished! Click Button to Take New Password!");
                        password.setEnabled(true);
                    }
                }.start();
            }
        });

    }

    //HELPER METHODS
    //this method kills application
    public void exit(View v){
        System.exit(0);
    }


    public String systemDate(String format)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }

}
