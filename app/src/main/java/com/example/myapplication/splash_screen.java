package com.example.myapplication;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class splash_screen extends AppCompatActivity {
    public Animation top_anim, bottom_anim;
    public TextView app_name;
    private static int SPLASH_SCREEN =1000;
    public static final String KEY_login_un = "Username_login";
    public static final String KEY_login_password = "password_login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //hooks
        top_anim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottom_anim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        app_name = findViewById(R.id.app_name);

        app_name.setAnimation(top_anim);

        SharedPreferences getshared = getSharedPreferences("user_login_detail",MODE_PRIVATE);
        String username = getshared.getString("username","null");
        String password = getshared.getString("password","null");
        Log.d("spref",""+username+" "+password);

        db_handler db = new db_handler(splash_screen.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<data> alldata = db.fetching();
                boolean done = false;
                for (data d : alldata){
                    if(username.equals(d.getUser_name()) && password.equals(d.getPassword())){
                        Intent intent1 = new Intent(splash_screen.this, MainActivity.class);
                        intent1.putExtra(KEY_login_un,username);
                        intent1.putExtra(KEY_login_password,password);
                        startActivity(intent1);
                        finish();
                        done = true;
                        break;
                    }
                }
                if (done == false){
                    Intent intent = new Intent(splash_screen.this, login.class);
                    startActivity(intent);
                    finish();
                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View,String>(app_name,"app_name_t");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(splash_screen.this,pairs);
                    startActivity(intent,options.toBundle());
                }
            }
        },SPLASH_SCREEN);
    }
}