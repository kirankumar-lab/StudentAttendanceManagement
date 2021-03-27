package com.pss.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;

import java.util.ArrayList;

public class Batch extends AppCompatActivity {

    private RecyclerView dataBatch;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListBatch> data;
    private dbSAMS db;

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

        myAdapter = new AdapterBatch(data);
        dataBatch.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addBatch(View view)
    {
        startActivity(new Intent(Batch.this, ManageBatch.class).putExtra("action","add"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        myAdapter.notifyDataSetChanged();
    }
}