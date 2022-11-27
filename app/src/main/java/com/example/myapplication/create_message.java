package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.List;

public class create_message extends AppCompatActivity {
    private EditText message;
    private AppCompatButton send_button;
    private db_handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        handler = new db_handler(create_message.this);
        message = findViewById(R.id.message_space);
        send_button = findViewById(R.id.message_send_button);

        // shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("message",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<data> alldata = handler.fetching();
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (data d : alldata){
                    editor.putString("msg",message.getText().toString());
                    editor.apply();
                    Toast.makeText(create_message.this, "Message send successfully", Toast.LENGTH_SHORT).show();
                }
                message.setText("");
            }
        });
    }
}