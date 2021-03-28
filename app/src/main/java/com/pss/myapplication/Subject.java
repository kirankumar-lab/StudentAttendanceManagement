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

public class Subject extends AppCompatActivity implements AdapterSubject.ItemClicked{

    private RecyclerView dataSubject;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListSubject> data;
    private dbSAMS db;
    private Dialog dialog;
    private Button btnCancle,btnYes;
    private TextView tvDeleteSubjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        db = new dbSAMS(this);

        dataSubject = findViewById(R.id.dataSubject);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        dataSubject.setLayoutManager(manager);

        data = new ArrayList<>();

        Cursor r = db.getAllSubject();

        while(r.moveToNext())
        {
            data.add(new ListSubject(Integer.parseInt(r.getString(0)),r.getString(1),
                    Integer.parseInt(r.getString(2)),Integer.parseInt(r.getString(3))));
        }

        myAdapter = new AdapterSubject(this,data);
        dataSubject.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addSubject(View view)
    {
        Intent i = new Intent(Subject.this, ManageSubject.class).putExtra("action","add");
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
            startActivity(new Intent(Subject.this,ManageSubject.class)
                    .putExtra("sbid",data.get(index).getSbid())
                    .putExtra("subject_name",data.get(index).getSubject_name())
                    .putExtra("semester",data.get(index).getSemester())
                    .putExtra("bid",data.get(index).getBid())
                    .putExtra("action",action));
        }
        if (action.equals("delete")){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.layout_delete_subject);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            btnCancle = dialog.findViewById(R.id.btnCancle);
            btnYes = dialog.findViewById(R.id.btnYes);
            tvDeleteSubjectName = dialog.findViewById(R.id.tvDeleteSubjectName);
            tvDeleteSubjectName.setText("Are You Sure to Delete \""+data.get(index).getSubject_name()+"\" Subject?");

            dialog.show();


            btnCancle.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnYes.setOnClickListener(v -> {
                String delete = db.deleteSubject(data.get(index).getSbid());
                Toast.makeText(this,delete,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                data.remove(index);
                myAdapter.notifyDataSetChanged();
            });
        }
    }
}