package com.pss.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Branch extends AppCompatActivity implements AdapterBranch.ItemClicked{

    private RecyclerView dataBranch;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListBranch> data;
    private dbSAMS db;
    private Dialog dialog;
    private Button btnCancle,btnYes;
    private TextView tvDeleteBranchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        db = new dbSAMS(this);

        dataBranch = findViewById(R.id.dataBranch);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        dataBranch.setLayoutManager(manager);

        data = new ArrayList<>();

        Cursor r = db.getAllBranch();

        while(r.moveToNext())
        {
            data.add(new ListBranch(Integer.parseInt(r.getString(0)),r.getString(1)));
        }

        myAdapter = new AdapterBranch(this,data);
        dataBranch.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addBranch(View view)
    {
        Intent i = new Intent(Branch.this, ManageBranch.class).putExtra("action","add");
        startActivityForResult(i,1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(requestCode == RESULT_OK)
            {
                myAdapter.notifyDataSetChanged();
            }
        }
    }

    private boolean isYes;

    @Override
    public void onItemClicked(int index,String action) {
        if (action.equals("edit")){
            startActivity(new Intent(Branch.this,ManageBranch.class)
                    .putExtra("bid",data.get(index).getBid())
                    .putExtra("branch_name",data.get(index).getBranch_name())
                    .putExtra("action",action));
        }
        if (action.equals("delete")){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.layout_delete_branch);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            btnCancle = dialog.findViewById(R.id.btnCancle);
            btnYes = dialog.findViewById(R.id.btnYes);
            tvDeleteBranchName = dialog.findViewById(R.id.tvDeleteBranchName);
            tvDeleteBranchName.setText("Are You Sure to Delete \""+data.get(index).getBranch_name()+"\" Branch?");

            dialog.show();


            btnCancle.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnYes.setOnClickListener(v -> {
                String delete = db.deleteBranch(data.get(index).getBid());
                Toast.makeText(this,delete,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                data.remove(index);
                myAdapter.notifyDataSetChanged();
                    });
        }
    }
}