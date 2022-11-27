package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class student extends AppCompatActivity {
    private PieChart pieChart;
    private RecyclerView recyclerView;
    private attendence_adapter adapter;
    private ArrayList<attendence_modle> attendence_modleArrayList;
    private attendence_db_handler handler;
    private String sap;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        handler = new attendence_db_handler(student.this);
        pieChart = findViewById(R.id.pie_chart);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendence_modleArrayList = new ArrayList<>();
        adapter = new attendence_adapter(attendence_modleArrayList,this);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        sap = intent.getStringExtra(MainActivity.KEY_SAP);

        setupPieChart();
        loadPieChartdata();
        Toast.makeText(this, "Click on the Chart to get your Attendence status", Toast.LENGTH_SHORT).show();
        // getting current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();


        List<attendence_data> alldata = handler.fetching();
        for (attendence_data d : alldata){
            Log.d("db_attend","Id: "+d.getId()+"\n"+"SAP : "+d.getSap()+"\n"+"Date: "+d.getDate()+
                    "\n"+"Present: "+d.getPresent()+"\n"+"Absent: "+d.getAbsent()+"\n"+"Leave: "+d.getLeave());
        }
    }
    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Attendence");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setEnabled(true);
    }

    private void loadPieChartdata(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f,"Present"));
        entries.add(new PieEntry(0.15f,"Leave"));
        entries.add(new PieEntry(0.10f,"Absent"));

        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }
        for(int color: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }

        PieDataSet dataset = new PieDataSet(entries,"");
        dataset.setColors(colors);

        PieData data = new PieData(dataset);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(e==entries.get(0)){
                    // present list
                    attendence_modleArrayList.clear();
                    List<attendence_data> alldata = handler.fetching();
                    for (attendence_data d : alldata){
                        if( sap.equals("("+d.getSap()+")") && d.getPresent()==1 && d.getAbsent() == 0 && d.getLeave() == 0){
                            attendence_modleArrayList.add(new attendence_modle(d.getDate(),"P"));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                else if(e == entries.get(1)){
                    // leave list
                    attendence_modleArrayList.clear();
                    List<attendence_data> alldata = handler.fetching();
                    for (attendence_data d : alldata){
                        if( sap.equals("("+d.getSap()+")") & d.getPresent()==0 && d.getAbsent() == 0 && d.getLeave() == 1){
                            Log.d("check_sap",""+sap.equals(d.getSap()));   // giving false i.e, error
                            attendence_modleArrayList.add(new attendence_modle(d.getDate(),"L"));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    // absent list
                    attendence_modleArrayList.clear();
                    List<attendence_data> alldata = handler.fetching();
                    for (attendence_data d : alldata){
                        if( sap.equals("("+d.getSap()+")") & d.getPresent()==0 && d.getAbsent() == 1 && d.getLeave() == 0){
                            Log.d("check_sap",""+sap.equals(d.getSap()));   // giving false i.e, error
                            attendence_modleArrayList.add(new attendence_modle(d.getDate(),"A"));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }


}