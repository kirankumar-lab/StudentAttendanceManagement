package com.pss.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Batch extends AppCompatActivity implements AdapterBatch.ItemClicked {

    private RecyclerView dataBatch;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListBatch> data;
    private dbSAMS db;
    private Dialog dialog;
    private Button btnCancle,btnYes;
    private TextView tvDeleteBatchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch);

        db = new dbSAMS(this);

        dataBatch = findViewById(R.id.dataBatch);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        dataBatch.setLayoutManager(manager);

        data = new ArrayList<>();

        Cursor r = db.getAllBatch();

        while(r.moveToNext())
        {
            data.add(new ListBatch(Integer.parseInt(r.getString(0)),r.getString(1)));
        }

        myAdapter = new AdapterBatch(this,data);
        dataBatch.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addBatch(View view)
    {
        Intent i = new Intent(Batch.this, ManageBatch.class).putExtra("action","add");
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

    @Override
    public void onItemClicked(int index,String action) {
        if (action.equals("edit")) {
            startActivity(new Intent(Batch.this, ManageBatch.class)
                    .putExtra("btid", data.get(index).getBtid())
                    .putExtra("batch_name", data.get(index).getBatch_name())
                    .putExtra("action", action));
        }
        if (action.equals("delete")) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.layout_delete_batch);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            btnCancle = dialog.findViewById(R.id.btnCancle);
            btnYes = dialog.findViewById(R.id.btnYes);
            tvDeleteBatchName = dialog.findViewById(R.id.tvDeleteBatchName);
            tvDeleteBatchName.setText("Are You Sure to Delete \"" + data.get(index).getBatch_name() +
                    "\" Batch?");

            dialog.show();


            btnCancle.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnYes.setOnClickListener(v -> {
                String delete = db.deleteBatch(data.get(index).getBtid());
                Toast.makeText(this, delete, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                data.remove(index);
                myAdapter.notifyDataSetChanged();
            });

        }
    }
}