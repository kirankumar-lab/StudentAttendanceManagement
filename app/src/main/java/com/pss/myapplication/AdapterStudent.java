package com.pss.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.ViewHolder> {
    private ArrayList<ListStudent> data;
    AdapterStudent.ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked(int index, String action);
    }

    public AdapterStudent(Context context, ArrayList<ListStudent> list) {
        data = list;
        activity = (AdapterStudent.ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dataStudentName, dataStudentMobileNo, dataStudentEmail,tvEdit, tvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataStudentName = itemView.findViewById(R.id.dataStudentName);
            dataStudentMobileNo = itemView.findViewById(R.id.dataStudentMobileNo);
            dataStudentEmail = itemView.findViewById(R.id.dataStudentEmail);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDelete = itemView.findViewById(R.id.tvDelete);


        }
    }

    @NonNull
    @Override
    public AdapterStudent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_student, parent,
                false);
        return new AdapterStudent.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStudent.ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));

        holder.dataStudentName.setText(data.get(position).getStudent_name());
        holder.dataStudentMobileNo.setText(String.valueOf(data.get(position).getStudent_mobileno()));
        holder.dataStudentEmail.setText(data.get(position).getStudent_email());

        holder.tvEdit.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListStudent) data.get(position)), "edit");
        });

        holder.tvDelete.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListStudent) data.get(position)), "delete");
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
