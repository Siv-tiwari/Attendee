package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class view_message extends AppCompatActivity {
    private TextView received_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        received_msg = findViewById(R.id.received_message);

        SharedPreferences getshared = getSharedPreferences("message",MODE_PRIVATE);
        String message = getshared.getString("msg","null");


//        Log.d("check_msg",""+message);

        received_msg.setText(message);
    }
}