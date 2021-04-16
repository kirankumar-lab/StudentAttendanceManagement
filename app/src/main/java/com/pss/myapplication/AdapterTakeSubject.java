package com.pss.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTakeSubject extends RecyclerView.Adapter<AdapterTakeSubject.ViewHolder> {

    private ArrayList<ListTakeSubject> data;
    private boolean isAdmin = false;
    ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked(int index, String action);
    }

    public AdapterTakeSubject(Context context, ArrayList<ListTakeSubject> list, boolean isAdmin) {
        data = list;
        this.isAdmin = isAdmin;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dataTakeSubjectName, dataTakeSubjectBatch, dataTakeSubjectSemester,
                dataTakeSubjectProfessor, tvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataTakeSubjectName = itemView.findViewById(R.id.dataTakeSubjectName);
            dataTakeSubjectBatch = itemView.findViewById(R.id.dataTakeSubjectBatch);
            dataTakeSubjectSemester = itemView.findViewById(R.id.dataTakeSubjectSemester);
            dataTakeSubjectProfessor = itemView.findViewById(R.id.dataTakeSubjectProfessor);
            tvDelete = itemView.findViewById(R.id.tvDelete);

        }
    }


    @NonNull
    @Override
    public AdapterTakeSubject.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (isAdmin == true) {
            v =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_take_subject_a, parent, false);
        } else {
            v =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_take_subject, parent, false);
        }
        return new AdapterTakeSubject.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTakeSubject.ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));

        holder.dataTakeSubjectName.setText(data.get(position).getSubject_name());
        holder.dataTakeSubjectSemester.setText(String.valueOf(data.get(position).getSemester()));
        holder.dataTakeSubjectBatch.setText(String.valueOf(data.get(position).getBatch_name()));
        if (isAdmin == true) {
            holder.dataTakeSubjectProfessor.setText(String.valueOf(data.get(position).getProfessor_name()));
        }

        holder.tvDelete.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListTakeSubject) data.get(position)), "delete");
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
