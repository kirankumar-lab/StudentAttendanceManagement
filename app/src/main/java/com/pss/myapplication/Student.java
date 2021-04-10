package com.pss.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Student extends AppCompatActivity implements AdapterStudent.ItemClicked {

    private RecyclerView dataStudent;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListStudent> data;
    private dbSAMS db;
    private Dialog dialog;
    private Button btnCancle, btnYes;
    private TextView tvDeleteStudentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        db = new dbSAMS(this);

        dataStudent = findViewById(R.id.dataStudent);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dataStudent.setLayoutManager(manager);

        data = new ArrayList<>();

        Cursor r = db.getAllStudent();

        while (r.moveToNext()) {
            data.add(new ListStudent(Integer.parseInt(r.getString(0)),r.getString(1),
                    r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getString(6),
                    Integer.parseInt(r.getString(7)),Integer.parseInt(r.getString(8)),
                    Integer.parseInt(r.getString(9))));
        }

        myAdapter = new AdapterStudent(this, data);
        dataStudent.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    public void addStudent(View view) {
        Intent i = new Intent(Student.this, ManageStudent.class).putExtra("action", "add");
        startActivityForResult(i, 1);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (requestCode == RESULT_OK) {
                myAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onItemClicked(int index, String action) {
        if (action.equals("edit")) {
            startActivity(new Intent(Student.this, ManageStudent.class)
                    .putExtra("stid", data.get(index).getStid())
                    .putExtra("student_name", data.get(index).getStudent_name())
                    .putExtra("student_mobileno",
                            data.get(index).getStudent_mobileno())
                    .putExtra("student_email", data.get(index).getStudent_email())
                    .putExtra("student_p_mobileno",
                            data.get(index).getStudent_p_mobileno())
                    .putExtra("student_p_email", data.get(index).getStudent_p_email())
                    .putExtra("student_password", data.get(index).getStudent_password())
                    .putExtra("btid", data.get(index).getBtid())
                    .putExtra("bid", data.get(index).getBid())
                    .putExtra("semester", data.get(index).getSemester())
                    .putExtra("action", action));
        }
        if (action.equals("delete")) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.layout_delete_student);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            btnCancle = dialog.findViewById(R.id.btnCancle);
            btnYes = dialog.findViewById(R.id.btnYes);
            tvDeleteStudentName = dialog.findViewById(R.id.tvDeleteStudentName);
            tvDeleteStudentName.setText("Are You Sure to Delete \"" + data.get(index).getStudent_name() + "\" Delete Details?");

            dialog.show();


            btnCancle.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnYes.setOnClickListener(v -> {
                /*---------------------------------------------------Change this Line---------------------------------------------------------------*/
                String delete = db.deleteStudent(data.get(index).getSid());
                Toast.makeText(this, delete, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                data.remove(index);
                myAdapter.notifyDataSetChanged();
            });
        }
    }
}