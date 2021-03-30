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

public class Lecture extends AppCompatActivity implements AdapterLecture.ItemClicked{

    private RecyclerView dataLecture;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListLecture> data;
    private dbSAMS db;
    private Dialog dialog;
    private Button btnCancle,btnYes;
    private TextView tvDeleteLectureType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        db = new dbSAMS(this);

        dataLecture = findViewById(R.id.dataLecture);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        dataLecture.setLayoutManager(manager);

        data = new ArrayList<>();

        Cursor r = db.getAllLecture();

        while(r.moveToNext())
        {
            data.add(new ListLecture(Integer.parseInt(r.getString(0)),r.getString(1)));
        }

        myAdapter = new AdapterLecture(this,data);
        dataLecture.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addLecture(View view)
    {
        try{
            Intent i = new Intent(Lecture.this, ManageLecture.class).putExtra("action","add");
            startActivityForResult(i,1);
        }
        catch(Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();
        }
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
            startActivity(new Intent(Lecture.this,ManageLecture.class)
                    .putExtra("lid",data.get(index).getLid())
                    .putExtra("lecture_type",data.get(index).getLecture_type())
                    .putExtra("action",action));
        }
        if (action.equals("delete")){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.layout_delete_lecture);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            btnCancle = dialog.findViewById(R.id.btnCancle);
            btnYes = dialog.findViewById(R.id.btnYes);
            tvDeleteLectureType = dialog.findViewById(R.id.tvDeleteLectureType);
            tvDeleteLectureType.setText("Are You Sure to Delete \""+data.get(index).getLecture_type()+"\" Lecture?");

            dialog.show();


            btnCancle.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnYes.setOnClickListener(v -> {
                String delete = db.deleteLecture(data.get(index).getLid());
                Toast.makeText(this,delete,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                data.remove(index);
                myAdapter.notifyDataSetChanged();
            });
        }
    }
}