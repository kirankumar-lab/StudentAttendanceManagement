package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class TakeAttend extends AppCompatActivity  implements AdapterStudentAttendanceList.ItemClicked {
    private dbSAMS db =  new dbSAMS(this);
    private int tsid,slot,sem,btid,bid;
    private String date,description;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListStudentAttendance> data;
    private MaterialToolbar toolbar;
    private CheckBox checkBox;
    private TextView student_id;
    private boolean isCheck = false;
    private int total;

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
        int total = myAdapter.getItemCount();

        toolbar = findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener (v->{
            finish();
        });

        toolbar.setOnMenuItemClickListener (item -> {
            if(item.getItemId() == R.id.selectAll){
                for (int i=0; i< total; i++)
                {
                    checkBox = recyclerView.getChildAt(i).findViewById(R.id.isPresent);
                    if(isCheck)
                    {
                        checkBox.setChecked(false);
                    }
                    else
                    {
                        checkBox.setChecked(true);
                    }
                }
                if (isCheck)
                {
                    isCheck = false;
                }
                else{
                    isCheck = true;
                }
            }
            if (item.getItemId() == R.id.saveAll) {
                String a = "";
                for (int i = 0; i < total; i++) {
                    checkBox = recyclerView.getChildAt(i).findViewById(R.id.isPresent);
                    student_id = recyclerView.getChildAt(i).findViewById(R.id.student_id);
                    if (checkBox.isChecked()) {
                        a += student_id.getText() + ",";
                    }
                }
                Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    @Override
    public void onItemClicked(int index, String action) {

    }
}