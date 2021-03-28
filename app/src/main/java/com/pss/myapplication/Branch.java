package com.pss.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Branch extends AppCompatActivity implements AdapterBranch.ItemClicked{

    private RecyclerView dataBranch;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListBranch> data;
    private dbSAMS db;

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

    @Override
    public void onItemClicked(int index) {
        startActivity(new Intent(Branch.this,ManageBranch.class)
                .putExtra("bid",data.get(index).getBid())
                .putExtra("branch_name",data.get(index).getBranch_name())
                .putExtra("action","edit"));
    }
}