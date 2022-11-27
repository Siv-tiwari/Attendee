package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class staff extends AppCompatActivity {
   private ImageButton manual_attendance, send_message,qr_attendance,schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        manual_attendance = findViewById(R.id.take_attendance);
        send_message = findViewById(R.id.send_message);
        qr_attendance = findViewById(R.id.qr_attendance);
        schedule = findViewById(R.id.teaching_schedule);

        manual_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0 = new Intent(staff.this,manual_take_attendance.class);
                startActivity(intent0);
            }
        });

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(staff.this,create_message.class);
                startActivity(intent1);
            }
        });

        qr_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(staff.this, QR_take_attendance.class);
                startActivity(intent2);
            }
        });
    }
}