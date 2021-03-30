package com.pss.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterLecture extends RecyclerView.Adapter<AdapterLecture.ViewHolder> {

    private ArrayList<ListLecture> data;
    ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked(int index, String action);
    }

    public AdapterLecture(Context context, ArrayList<ListLecture> list) {
        data = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dataLectureType, tvEdit, tvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataLectureType = itemView.findViewById(R.id.dataLecturetype);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDelete = itemView.findViewById(R.id.tvDelete);


        }
    }


    @NonNull
    @Override
    public AdapterLecture.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lecture, parent,
                false);
        return new AdapterLecture.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLecture.ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));

        holder.dataLectureType.setText(data.get(position).getLecture_type());

        holder.tvEdit.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListLecture) data.get(position)), "edit");
        });

        holder.tvDelete.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListLecture) data.get(position)), "delete");
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
