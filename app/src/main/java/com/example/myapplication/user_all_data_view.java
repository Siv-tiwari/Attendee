package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class user_all_data_view extends AppCompatActivity {
    private ImageView change_password_image, change_user_image , user_photo;
    private TextView user_detail_fullname, user_detail_username, user_detail_sap, user_detail_rollno;
    private AppCompatButton select_image;
    private static final int GALLARY_REQUEST_CODE = 123;
    String sap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all_data_view);
        user_photo = findViewById(R.id.user_photo);
        change_password_image = findViewById(R.id.change_password_image);
        change_user_image = findViewById(R.id.change_user_image);
        user_detail_fullname = findViewById(R.id.user_detail_fullname);
        user_detail_username = findViewById(R.id.user_detail_username);
        user_detail_sap = findViewById(R.id.user_detail_sap);
        user_detail_rollno = findViewById(R.id.user_detail_rollno);
        select_image = findViewById(R.id.select_image);

        db_handler db = new db_handler(user_all_data_view.this);
        Intent intent = getIntent();
        sap = intent.getStringExtra(MainActivity.KEY_SAP);
        List<data> alldata = db.fetching();
        for (data d : alldata){
            if(sap.equals("("+d.getSap()+")")){
                user_detail_fullname.setText(d.getFull_name());
                user_detail_username.setText("Username: "+d.getUser_name());
                user_detail_sap.setText("SAP: "+d.getSap());
                user_detail_rollno.setText("Roll no.: "+d.getRoll_no());
                break;
            }
        }
        change_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log_out = new Intent(user_all_data_view.this,login.class);
                log_out.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(log_out);
            }
        });

        change_password_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(user_all_data_view.this);
                View password_reset_botton_sheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.new_password_reset,(RelativeLayout)findViewById(R.id.password_reset_RL));
                EditText old_password = password_reset_botton_sheet.findViewById(R.id.old_password);
                EditText new_password = password_reset_botton_sheet.findViewById(R.id.new_password);
                AppCompatButton btn = password_reset_botton_sheet.findViewById(R.id.change_password_btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (old_password.getText().toString().equals(new_password.getText().toString())) {
                            Toast.makeText(user_all_data_view.this, "Same as the previous password", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();
                        } else {
                            // update password
                            boolean flag = false;
                            List<data> alldata = db.fetching();
                            for (data d : alldata) {
                                if (sap.equals("(" + d.getSap() + ")") && old_password.getText().toString().equals(d.getPassword())) {
                                    db.update_data(new_password.getText().toString(),old_password.getText().toString());
                                    Toast.makeText(user_all_data_view.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                    bottomSheetDialog.dismiss();
                                    flag = true;
                                    break;
                                }
                            }
                            if(flag == false){
                                Toast.makeText(user_all_data_view.this, "Please enter the correct old password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setContentView(password_reset_botton_sheet);
                bottomSheetDialog.show();
            }
        });
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_image.setVisibility(View.GONE);
                Intent intent_image = new Intent();
                intent_image.setType("image/*");        // helps to select any type of image
                intent_image.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent_image,"pick an image"),GALLARY_REQUEST_CODE);
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLARY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            Uri imagedata = data.getData();
            user_photo.setImageURI(imagedata);
            user_photo.setClipToOutline(true);
        }
    }
}