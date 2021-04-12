package com.pss.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TakeSubject extends AppCompatActivity implements AdapterTakeSubject.ItemClicked{

    private RecyclerView dataTakeSubject;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListTakeSubject> data;
    private dbSAMS db;
    private Dialog dialog;
    private Button btnCancle,btnYes;
    private TextView tvDeleteSubjectName;

    private String prof_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_subject);

        prof_id = getIntent().getStringExtra("prof_id");

        db = new dbSAMS(this);

        dataTakeSubject = findViewById(R.id.dataTakeSubject);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        dataTakeSubject.setLayoutManager(manager);

        data = new ArrayList<>();

        Cursor r = db.getTakeSubject(prof_id);

        while(r.moveToNext())
        {
            data.add(new ListTakeSubject(Integer.parseInt(r.getString(0)),
                    String.valueOf(db.getTakeSubjectName(r.getString(4))),
                            Integer.parseInt(db.getTakeSubjectSemester(r.getString(4))),
                    Integer.parseInt(r.getString(1)),
                                    Integer.parseInt(r.getString(2)),
                                            Integer.parseInt(r.getString(3)),
                                                    Integer.parseInt(r.getString(5))));
        }

        myAdapter = new AdapterTakeSubject(this,data);
        dataTakeSubject.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addSubject(View v) {
        Intent i  = new Intent(TakeSubject.this, ManageTakeSubject.class);
        i.putExtra("prof_id",prof_id);
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

//    private boolean isYes;

    @Override
    public void onItemClicked(int index,String action) {
        if (action.equals("delete")){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.layout_delete_take_subject);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            btnCancle = dialog.findViewById(R.id.btnCancle);
            btnYes = dialog.findViewById(R.id.btnYes);
            tvDeleteSubjectName = dialog.findViewById(R.id.tvDeleteTakeSubjectName);
            tvDeleteSubjectName.setText("Are You Sure to Delete \""+data.get(index).getSubject_name()+"\" Subject?");

            dialog.show();


            btnCancle.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnYes.setOnClickListener(v -> {
                String delete = db.deleteTakeSubject(String.valueOf(data.get(index).getTsid()));
                Toast.makeText(this,delete,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                data.remove(index);
                myAdapter.notifyDataSetChanged();
            });
        }
    }
}