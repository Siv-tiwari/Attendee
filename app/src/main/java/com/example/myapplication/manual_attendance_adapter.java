package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class manual_attendance_adapter extends RecyclerView.Adapter<manual_attendance_adapter.ViewHolder> {
    private ArrayList<attendence_modle> manual_list;
    private Context context;
    private attendence_db_handler handler;

    public manual_attendance_adapter(ArrayList<attendence_modle> manual_list, Context context) {
        this.manual_list = manual_list;
        this.context = context;
    }

    @NonNull
    @Override
    public manual_attendance_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manual_attendance_recycle_view_layout,parent,false);
        handler = new attendence_db_handler(context);
        return new manual_attendance_adapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        attendence_modle modle = manual_list.get(position);
        holder.student_name.setText(modle.getUser_name());
        holder.student_sap.setText(modle.getUser_sap());
        holder.check_box.setChecked(modle.getCheck_box());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        List<attendence_data> att_data = handler.fetching();


        holder.check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.check_box.setChecked(holder.check_box.isChecked());

                if(holder.check_box.isChecked() == true){
                    modle.setCheck_box(true);
                    boolean flag = false;
                    for(attendence_data d : att_data){
                        if( modle.getUser_sap().equals(d.getSap()) && modle.getCheck_box() && d.getPresent()==0 && d.getAbsent() == 0 && d.getLeave() == 0){
                            attendence_data data_present = new attendence_data();
                            data_present.setSap(modle.getUser_sap());
                            data_present.setDate(dtf.format(now));
                            data_present.setPresent(1);
                            data_present.setAbsent(0);
                            data_present.setLeave(0);
                            handler.add_user(data_present);
                            Toast.makeText(context, "Marked", Toast.LENGTH_SHORT).show();
                            flag = true;
                            break;
                        }
                    }
                    if(flag == false){
                        attendence_data data_present = new attendence_data();
                        data_present.setSap(modle.getUser_sap());
                        data_present.setDate(dtf.format(now));
                        data_present.setPresent(1);
                        data_present.setAbsent(0);
                        data_present.setLeave(0);
                        handler.add_user(data_present);
                    }
                }
                else{
                    modle.setCheck_box(false);
                    for(attendence_data new_d : att_data){
                        if( modle.getUser_sap().equals(new_d.getSap()) && !modle.getCheck_box() && new_d.getAbsent() == 0 && new_d.getLeave() == 0){
                            handler.update_data(0,dtf.format(now));
                            Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
//                    Toast.makeText(context, ""+modle.getCheck_box(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return manual_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView student_name,student_sap;
        private CheckBox check_box;
        public ViewHolder(View view) {
            super(view);
            student_name = view.findViewById(R.id.student_name);
            student_sap = view.findViewById(R.id.student_sap);
            check_box = view.findViewById(R.id.check_box);
        }
    }
}

