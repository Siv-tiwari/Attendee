package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class manual_take_attendance extends AppCompatActivity {
    private RecyclerView manual_recycle_view;
    private manual_attendance_adapter adapter;
    private ArrayList<attendence_modle> manual_arraylist;
    private db_handler handler;
    private attendence_db_handler handler2;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_take_attendance);
        handler = new db_handler(manual_take_attendance.this);
        handler2 = new attendence_db_handler(manual_take_attendance.this);
        manual_recycle_view = findViewById(R.id.manual_recycle_view);
        manual_recycle_view.setLayoutManager(new LinearLayoutManager(this));
        manual_arraylist = new ArrayList<>();
        adapter = new manual_attendance_adapter(manual_arraylist,this);
        manual_recycle_view.setAdapter(adapter);

        // getting current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

//        manual_arraylist.clear();
        List<data> alldata = handler.fetching();
        List<attendence_data> att_data = handler2.fetching();
        boolean check_marked = false;
        for (data d : alldata){
            for(attendence_data present_check : att_data){
                if(dtf.format(now).equals(present_check.getDate()) && present_check.getPresent() == 1 && present_check.getSap().equals(d.getSap())){
                    check_marked = true;
                    Toast.makeText(this, "gotted", Toast.LENGTH_SHORT).show();
                }
            }
            manual_arraylist.add(new attendence_modle(d.getFull_name(),d.getSap(),check_marked));
            check_marked = false;
        }
        adapter.notifyDataSetChanged();


    }
}