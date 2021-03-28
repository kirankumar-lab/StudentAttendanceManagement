package com.pss.myapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterBatch extends RecyclerView.Adapter<AdapterBatch.ViewHolder> {

    private ArrayList<ListBatch> data;
    ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked(int index, String action);
    }

    public AdapterBatch(Context context, ArrayList<ListBatch> list) {
        data = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dataBatchName, tvEdit, tvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataBatchName = itemView.findViewById(R.id.dataBatchName);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDelete = itemView.findViewById(R.id.tvDelete);


        }
    }


    @NonNull
    @Override
    public AdapterBatch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_batch, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBatch.ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));

        holder.dataBatchName.setText(data.get(position).getBatch_name());

        holder.tvEdit.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListBatch) data.get(position)), "edit");
        });

        holder.tvDelete.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListBatch) data.get(position)), "delete");
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
