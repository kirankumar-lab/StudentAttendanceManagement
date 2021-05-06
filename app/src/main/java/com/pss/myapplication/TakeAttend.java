package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class TakeAttend extends AppCompatActivity  implements AdapterStudentAttendanceList.ItemClicked {
    private dbSAMS db =  new dbSAMS(this);
    private int tsid,slot,sem,btid,bid;
    private String date,description;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListStudentAttendance> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attend);

        tsid=getIntent().getIntExtra("tsid",0);
        slot=getIntent().getIntExtra("slot",0);
        date=getIntent().getStringExtra("date");
        sem=getIntent().getIntExtra("sem",0);
        description=getIntent().getStringExtra("description");

        recyclerView = findViewById(R.id.student_list);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        data = new ArrayList<>();

        Cursor cursor = db.getStudentListByTakeAttend(tsid);
        int no = 1;
        while (cursor.moveToNext()) {
            data.add(new ListStudentAttendance(cursor.getInt(0),cursor.getString(1),false,no));
            no++;
        }
        cursor.close();
        myAdapter = new AdapterStudentAttendanceList(this, data);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int index, String action) {

    }
}