package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class login extends AppCompatActivity {
    AppCompatButton submit,signup;
    TextInputLayout username , password;
    public static final String KEY_login_un = "Username_login";
    public static final String KEY_login_password = "password_login";
    public static final String  KEY_CHECK = "check";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        submit = findViewById(R.id.submit);
        signup = findViewById(R.id.signup);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        db_handler db = new db_handler(login.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<data> alldata = db.fetching();
                boolean done = false;
                for (data d : alldata){
                    if(username.getEditText().getText().toString().equals(d.getUser_name()) && password.getEditText().getText().toString().equals(d.getPassword())){
                        Intent intent = new Intent(login.this, MainActivity.class);
                        intent.putExtra(KEY_CHECK,"true");
                        intent.putExtra(KEY_login_un,username.getEditText().getText().toString());
                        intent.putExtra(KEY_login_password,password.getEditText().getText().toString());
                        startActivity(intent);
                        finish();
                        done = true;
                        break;
                    }
                }
                if (done == false){
                    Toast.makeText(login.this, "Wrong username and password\nNew user? sign up", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, new_user_signup.class);
                startActivity(intent);
                finish();
            }
        });

    }
}