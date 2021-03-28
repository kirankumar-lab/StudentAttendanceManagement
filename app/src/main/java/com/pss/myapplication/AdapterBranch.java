package com.pss.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterBranch extends RecyclerView.Adapter<AdapterBranch.ViewHolder> {

    private ArrayList<ListBranch> data;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public AdapterBranch (Context context, ArrayList<ListBranch> list)
    {
        data = list;
        activity  = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dataBranchName,tvEdit,tvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataBranchName = itemView.findViewById(R.id.dataBranchName);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            //tvDelete = itemView.findViewById(R.id.tvDelete);



        }
    }


    @NonNull
    @Override
    public AdapterBranch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_branch,parent,
                    false);
        return new AdapterBranch.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBranch.ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));

        holder.dataBranchName.setText(data.get(position).getBranch_name());

        holder.tvEdit.setOnClickListener(v -> {
            activity.onItemClicked(data.indexOf((ListBranch) data.get(position)));
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
