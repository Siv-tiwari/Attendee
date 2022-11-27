package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class new_user_signup extends AppCompatActivity {
    public TextInputLayout name,username,sap,rollno,password,confirm_password;
    public AppCompatButton signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db_handler db = new db_handler(new_user_signup.this);
        name = findViewById(R.id.Full_Name);
        username = findViewById(R.id.user);
        sap = findViewById(R.id.Sap);
        rollno = findViewById(R.id.rollno);
        password = findViewById(R.id.user_password);
        confirm_password = findViewById(R.id.confirm_user_password);
        signup = findViewById(R.id.sign_up);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList data = new ArrayList<>();
                if(password.getEditText().getText().toString().equals(confirm_password.getEditText().getText().toString())){
                    // adding
                    com.example.myapplication.data d1 = new data();
                    d1.setFull_name(name.getEditText().getText().toString());
                    d1.setUser_name(username.getEditText().getText().toString());
                    d1.setSap(sap.getEditText().getText().toString());
                    d1.setRoll_no(rollno.getEditText().getText().toString());
                    d1.setPassword(password.getEditText().getText().toString());
                    db.add_user(d1);

                    Intent in = new Intent(new_user_signup.this, login.class);
                    startActivity(in);
                    finish();
                }
                else{
                    Toast.makeText(new_user_signup.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // fetching
        List<data> alldata = db.fetching();
        for (data d : alldata){
            Log.d("db_siv","Id: "+d.getId()+"\n"+"Full name: "+d.getFull_name()+"\n"+"Username: "+d.getUser_name()+
                    "\n"+"Sap: "+d.getSap()+"\n"+"Roll no: "+d.getRoll_no()+"\n"+"Password: "+d.getPassword());
        }
    }
}
