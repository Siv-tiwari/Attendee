package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class attendence_adapter extends RecyclerView.Adapter<attendence_adapter.ViewHolder> {
    private ArrayList<attendence_modle> list;
    private Context context;

    public attendence_adapter(ArrayList<attendence_modle> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public attendence_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendence_recycle_view_layout,parent,false);
        return new attendence_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull attendence_adapter.ViewHolder holder, int position) {
        attendence_modle modle = list.get(position);
        holder.date.setText(modle.getDate());
        holder.status.setText(modle.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.linear_layout_date);
            status = itemView.findViewById(R.id.linear_layout_status);
        }
    }
}
