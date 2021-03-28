package com.pss.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.ViewHolder> {

    private ArrayList<ListSubject> data;
    ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked(int index, String action);
    }

    public AdapterSubject(Context context, ArrayList<ListSubject> list) {
        data = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dataSubjectName,dataSubjectBranch,dataSubjectSemester, tvEdit, tvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataSubjectName = itemView.findViewById(R.id.dataSubjectName);
            dataSubjectBranch = itemView.findViewById(R.id.dataSubjectBranch);
            dataSubjectSemester = itemView.findViewById(R.id.dataSubjectSemester);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDelete = itemView.findViewById(R.id.tvDelete);


        }
    }


    @NonNull
    @Override
    public AdapterSubject.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subject, parent,
                false);
        return new AdapterSubject.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubject.ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));

        holder.dataSubjectName.setText(data.get(position).getSubject_name());
        holder.dataSubjectSemester.setText(String.valueOf(data.get(position).getSemester()));
        holder.dataSubjectBranch.setText(String.valueOf(data.get(position).getBid()));

        holder.tvEdit.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListSubject) data.get(position)), "edit");
        });

        holder.tvDelete.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListSubject) data.get(position)), "delete");
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
