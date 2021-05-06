package com.pss.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterStudentAttendanceList extends RecyclerView.Adapter<AdapterStudentAttendanceList.ViewHolder> {
    private ArrayList<ListStudentAttendance> data;
    AdapterStudentAttendanceList.ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked(int index, String action);
    }

    public AdapterStudentAttendanceList(Context context, ArrayList<ListStudentAttendance> list) {
        data = list;
        activity = (AdapterStudentAttendanceList.ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rollNo,student_name,student_id;
        CheckBox isPresent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rollNo = itemView.findViewById(R.id.rollNo);
            student_name = itemView.findViewById(R.id.student_name);
            isPresent = itemView.findViewById(R.id.isPresent);
            student_id = itemView.findViewById(R.id.student_id);
        }
    }

    @NonNull
    @Override
    public AdapterStudentAttendanceList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_student_attendance_list, parent,
                false);
        return new AdapterStudentAttendanceList.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStudentAttendanceList.ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));
        holder.rollNo.setText(String.valueOf(data.get(position).getRollNo()));
        holder.student_id.setText(String.valueOf(data.get(position).getSid()));
        holder.student_name.setText(data.get(position).getName());
        holder.isPresent.setChecked(data.get(position).isPresent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
