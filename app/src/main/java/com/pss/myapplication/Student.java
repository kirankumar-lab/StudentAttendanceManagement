package com.pss.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    RecyclerView.Adapter myAdapter2;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListStudent> data;
    private ArrayList<ListStudent> data2;
    private dbSAMS db;
    private Dialog dialog;
    private Button btnCancle, btnYes;
    private TextView tvDeleteStudentName;
    private String selectedBranch,selectedBatch;
    private int btid,bid;
    private String userType;

    private AutoCompleteTextView actvBatch,actvBranch;

    private ArrayList<String> fetchedBatch = new ArrayList<>();
    private ArrayList<String> fetchedBranch = new ArrayList<>();

    private ArrayAdapter<String> fetchedBatchAdap,fetchedBranchAdap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        actvBatch = findViewById(R.id.actvBatch);
        actvBranch = findViewById(R.id.actvBranch);


        db = new dbSAMS(this);

        dataStudent = findViewById(R.id.dataStudent);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dataStudent.setLayoutManager(manager);

        // for batch dropdown //////////////////////////////////////////////////////////////////////
        Cursor cursor = db.getAllBatch();
        while (cursor.moveToNext()) {
            fetchedBatch.add(cursor.getString(cursor.getColumnIndex("batch_name")));
        }
        cursor.close();

        fetchedBatchAdap = new ArrayAdapter<>(this, R.layout.dropdown_menu, fetchedBatch);
        actvBatch.setAdapter(fetchedBatchAdap);


        // for branch dropdown //////////////////////////////////////////////////////////////////////
        Cursor cursor1 = db.getAllBranch();
        while (cursor1.moveToNext()) {
            fetchedBranch.add(cursor1.getString(cursor1.getColumnIndex("branch_name")));
        }
        cursor1.close();

        fetchedBranchAdap = new ArrayAdapter<>(this, R.layout.dropdown_menu, fetchedBranch);
        actvBranch.setAdapter(fetchedBranchAdap);


        ///////////////////////////////////////////////////////////////////////////////////////////

        data = new ArrayList<>();
        userType = getIntent().getStringExtra("UserType");

        if (userType.equals("admin") && userType != null) {
            Cursor r = db.getAllStudent();

            while (r.moveToNext()) {
                data.add(new ListStudent(Integer.parseInt(r.getString(0)), r.getString(1),
                        r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6),
                        Integer.parseInt(r.getString(9)), Integer.parseInt(r.getString(8)),
                        Integer.parseInt(r.getString(7))));
            }
            r.close();

            myAdapter = new AdapterStudent(this, data);
            dataStudent.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }
    }
    // get student data by filter
    public void showList(View view) {
        data.clear();

        selectedBatch = actvBatch.getText().toString();
       // btid = Integer.parseInt(db.getBatchID(selectedBatch));

        selectedBranch = actvBranch.getText().toString();
        //bid = Integer.parseInt(db.getBranchID(selectedBranch));

        //Toast.makeText(this, selectedBatch + selectedBranch, Toast.LENGTH_SHORT).show();
        data = new ArrayList<>();

        dataStudent.swapAdapter(myAdapter,true);

        Cursor r1 = db.getAllStudentList(selectedBranch,selectedBatch);
        while (r1.moveToNext()) {
            data.add(new ListStudent(Integer.parseInt(r1.getString(0)),r1.getString(1),
                    r1.getString(2),r1.getString(3),r1.getString(4),r1.getString(5),r1.getString(6),
                    Integer.parseInt(r1.getString(9)),Integer.parseInt(r1.getString(8)),
                    Integer.parseInt(r1.getString(7))));
        }
        r1.close();


        myAdapter = new AdapterStudent(this, data);
        dataStudent.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }



    public void addStudent(View view) {
        Intent i =
                new Intent(Student.this, ManageStudent.class).putExtra("action", "add").putExtra(
                        "user",userType);
        startActivityForResult(i, 1);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(userType.equals("admin")) {
            myAdapter.notifyDataSetChanged();
        }
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
                    .putExtra("action", action)
            .putExtra("user",userType));
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
                String delete = db.deleteStudent(data.get(index).getStid());
                Toast.makeText(this, delete, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                data.remove(index);
                myAdapter.notifyDataSetChanged();
            });
        }
    }
}