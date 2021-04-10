package com.pss.myapplication;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageSubject extends AppCompatActivity {

    private dbSAMS db = new dbSAMS(this);
    private TextInputEditText edtSubjectName;
    private TextView tvAction;
    private Button btnSubject;
    private String subjectName;
    private int sbid;
    private String subject_name;
    private String subjectBranch;
    private String subjectSemester;
    private int bid;
    private int semester;

    private AutoCompleteTextView actvSelectBranch;
    private AutoCompleteTextView actvSelectSemester;

    private ArrayList<String> listBranch = new ArrayList<>();
    private ArrayList<Integer> listSelect = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_subject);

        Cursor cursor = db.getAllBranch();

        while (cursor.moveToNext()) {
            listBranch.add(cursor.getString(cursor.getColumnIndex("branch_name")));
        }
        cursor.close();

        for (int i = 1; i <= 8; i++) {
            listSelect.add(i);
        }

        ArrayAdapter<String> autoBranch = new ArrayAdapter<>(this, R.layout.dropdown_menu, listBranch);
        ArrayAdapter<Integer> autoSelect = new ArrayAdapter<>(this, R.layout.dropdown_menu, listSelect);

        actvSelectBranch = findViewById(R.id.actvSelectBranch);
        actvSelectBranch.setAdapter(autoBranch);
        actvSelectSemester = findViewById(R.id.actvSelectSemester);
        actvSelectSemester.setAdapter(autoSelect);

        edtSubjectName = findViewById(R.id.tietSubjectName);
        tvAction = findViewById(R.id.textAction);
        btnSubject = findViewById(R.id.btnSubject);


        String action = getIntent().getStringExtra("action");


        if (action.equals("add")) {
            tvAction.setText("Add Subject");
            btnSubject.setText("Insert");
        }
        if (action.equals("edit")) {
            try {
                sbid = getIntent().getIntExtra("sbid", 0);
                subject_name = getIntent().getStringExtra("subject_name");
                edtSubjectName.setText(subject_name);
                tvAction.setText("Edit Subject");
                btnSubject.setText("Update");
            } catch (Exception ex) {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
            }
        }
        /*if (action.equals("delete")) {
            sbid = getIntent().getIntExtra("sbid", 0);
            subject_name = getIntent().getStringExtra("subject_name");

            tvAction.setText("Delete Subject");
            btnSubject.setText("Delete");
        }*/
        btnSubject.setOnClickListener(v -> {
            subjectName = edtSubjectName.getText().toString().trim();
            subjectBranch = actvSelectBranch.getText().toString().trim();
            subjectSemester = actvSelectSemester.getText().toString().trim();

            Pattern p = Pattern.compile("[a-zA-Z\\s]{3,}");
            Matcher m = p.matcher(subjectName);
            boolean match = m.matches();

            if (action.equals("add")) {
                if (!subjectName.isEmpty() && !subjectBranch.isEmpty() && !subjectSemester.isEmpty()) {
                    if (match) {
                        if (db.isSubjectAlready(subjectName,subjectBranch)) {
                            Toast.makeText(this, "Subject already exists !", Toast.LENGTH_SHORT).show();
                        } else {
                            semester = Integer.parseInt(subjectSemester);
                            bid = Integer.parseInt(db.getBranchID(subjectBranch));
                            String insert = db.insertSubject(subjectName.trim(), bid, semester);

                            Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageSubject.this, Subject.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Enter Subject Name minimum 3 Characters Length",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
            }

            if (action.equals("edit")) {
                if (!subjectName.isEmpty() && !subjectBranch.isEmpty() && !subjectSemester.isEmpty()) {
                    if (match) {
                        if (db.subjectCount(subjectName,subjectBranch) >1) {
                            Toast.makeText(this, "Subject already exists !", Toast.LENGTH_SHORT).show();
                        } else {
                            semester = Integer.parseInt(subjectSemester);
                            bid = Integer.parseInt(db.getBranchID(subjectBranch));
                            String result = db.updateSubject(sbid, subjectName, bid, semester);
                            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageSubject.this, Subject.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Enter Subject Name minimum 3 Characters Length",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Fill All The Blanks!", Toast.LENGTH_SHORT).show();
                }
            }
/*
            if (action.equals("delete")) {
                String result = db.deleteSubject(sbid);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ManageSubject.this, Subject.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }

 */
        });
    }
}