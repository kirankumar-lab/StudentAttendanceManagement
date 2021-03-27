package com.pss.myapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterBatch extends RecyclerView.Adapter<AdapterBatch.ViewHolder>{

    private ArrayList<ListBatch> data;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public AdapterBatch (ArrayList<ListBatch> list)
    {
        data = list;
//        activity  = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dataBatchName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataBatchName = itemView.findViewById(R.id.dataBatchName);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    activity.onItemClicked(data.indexOf((ListBatch) v.getTag() ));
//                }
//            });

        }
    }


    @NonNull
    @Override
    public AdapterBatch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_batch,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBatch.ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));

        holder.dataBatchName.setText(data.get(position).getBatch_name());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
