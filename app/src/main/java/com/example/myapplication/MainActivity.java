package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    private GifImageView scan,qr,view_message;
    private TextView about_user,user_sap;
    private ImageView user_data_view;
    private Handler handler;
    private CardView c1,c2,c3,c4;
    String security_key_staff = "123";
    String username,password , check="false";
    public static final String USER_NAME_KEY = "user_name_key";
    public static final String KEY_SAP = "user_sap_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        scan = findViewById(R.id.Scan);
        qr = findViewById(R.id.your_qr);
        c1 = findViewById(R.id.student_card);
        c2 = findViewById(R.id.staff_card);
        c3 = findViewById(R.id.class_schedule_card);
        c4 = findViewById(R.id.chat_card);
        user_data_view = findViewById(R.id.user_data_view);
        view_message = findViewById(R.id.view_message_gif);
        db_handler db = new db_handler(MainActivity.this);
        about_user = findViewById(R.id.about_user);
        user_sap = findViewById(R.id.sap);

        Intent intent = getIntent();
        if(check.equals(intent.getStringExtra(login.KEY_CHECK))){
            username = intent.getStringExtra(login.KEY_login_un);
            password = intent.getStringExtra(login.KEY_login_password);
        }
        else{
            username = intent.getStringExtra(splash_screen.KEY_login_un);
            password = intent.getStringExtra(splash_screen.KEY_login_password);
        }
        List<data> alldata = db.fetching();
        for (data d : alldata){
            if(username.equals(d.getUser_name()) && password.equals(d.getPassword())){
                about_user.setText("Welcome "+d.getFull_name());
                user_sap.setText("("+d.getSap()+")");
                break;
            }
        }

        gif_controller(scan,R.drawable.scan_still_image);
        gif_controller(qr,R.drawable.still_icon);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_scan = new Intent(MainActivity.this, scan_qr.class);
                intent_scan.putExtra(KEY_SAP,user_sap.getText().toString());
                startActivity(intent_scan);
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_qr = new Intent(MainActivity.this, generate_qr.class);
                intent_qr.putExtra(KEY_SAP,user_sap.getText().toString());
                intent_qr.putExtra(USER_NAME_KEY,username);
                startActivity(intent_qr);
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stud_intent = new Intent(MainActivity.this, student.class);
                stud_intent.putExtra(KEY_SAP,user_sap.getText().toString());
                startActivity(stud_intent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                View bottomsheetview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.security_key_check,(LinearLayout)findViewById(R.id.check));
                EditText code = bottomsheetview.findViewById(R.id.security_key);
                AppCompatButton code_submit = bottomsheetview.findViewById(R.id.security_key_submit);
                code_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(code.getText().toString().equals(security_key_staff)){
                            Intent staff_intent = new Intent(MainActivity.this,staff.class);
                            startActivity(staff_intent);
                            bottomSheetDialog.dismiss();
                        }
                        else{
                            bottomSheetDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Wrong Key! Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setContentView(bottomsheetview);
                bottomSheetDialog.show();
            }
        });

        // view message section
        SharedPreferences getshared = getSharedPreferences("message",MODE_PRIVATE);
        view_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent view_msg = new Intent(MainActivity.this, com.example.myapplication.view_message.class);
                startActivity(view_msg);
            }
        });

        user_data_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_data = new Intent(MainActivity.this,user_all_data_view.class);
                user_data.putExtra(KEY_SAP,user_sap.getText().toString());
                startActivity(user_data);
            }
        });

        // shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("user_login_detail",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor.putString("username",username);
        editor1.putString("password",password);
        editor.apply();
        editor1.apply();

    }
    public void gif_controller(GifImageView x,int image){
        handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                x.setImageDrawable(getDrawable(image));
            }
        };
        handler.postDelayed(r,1450);
    }
}