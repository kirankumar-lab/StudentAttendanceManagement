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
    private ArrayList<ListProfessor> data;
    AdapterStudent.ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked(int index, String action);
    }

    public AdapterStudent(Context context, ArrayList<ListProfessor> list) {
        data = list;
        activity = (AdapterStudent.ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dataProfessorName, dataProfessorMobileNo, dataProfessorEmail, dataProfessorBranch,
                tvEdit, tvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataProfessorName = itemView.findViewById(R.id.dataProfessorName);
            dataProfessorMobileNo = itemView.findViewById(R.id.dataProfessorMobileNo);
            dataProfessorEmail = itemView.findViewById(R.id.dataProfessorEmail);
            dataProfessorBranch = itemView.findViewById(R.id.dataProfessorBranch);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDelete = itemView.findViewById(R.id.tvDelete);


        }
    }

    @NonNull
    @Override
    public AdapterStudent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_professor, parent,
                false);
        return new AdapterStudent.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStudent.ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));

        holder.dataProfessorName.setText(data.get(position).getProfessor_name());
        holder.dataProfessorMobileNo.setText(String.valueOf(data.get(position).getProfessor_mobileno()));
        holder.dataProfessorEmail.setText(data.get(position).getProfessor_email());
        holder.dataProfessorBranch.setText(String.valueOf(data.get(position).getBid()));

        holder.tvEdit.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListProfessor) data.get(position)), "edit");
        });

        holder.tvDelete.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListProfessor) data.get(position)), "delete");
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
