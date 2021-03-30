package com.pss.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageLecture extends AppCompatActivity {

    private dbSAMS db = new dbSAMS(this);
    private TextInputEditText edtLectureType;
    private TextView tvAction;
    private Button btnLecture;
    private String lectureType;
    private int lid;
    private String lecture_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lecture);


        edtLectureType = findViewById(R.id.tietLectureType);
        tvAction = findViewById(R.id.textAction);
        btnLecture = findViewById(R.id.btnLecture);


        String action = getIntent().getStringExtra("action");


        if (action.equals("add")) {
            tvAction.setText("Add Lecture");
            btnLecture.setText("Insert");
        }
        if (action.equals("edit")) {

            lid = getIntent().getIntExtra("lid", 0);
            lecture_type = getIntent().getStringExtra("lecture_type");
            edtLectureType.setText(lecture_type);

            tvAction.setText("Edit Lecture");
            btnLecture.setText("Update");
        }
        if (action.equals("delete")) {
            lid = getIntent().getIntExtra("lid", 0);
            lecture_type = getIntent().getStringExtra("lecture_type");

            tvAction.setText("Delete Lecture");
            btnLecture.setText("Delete");
        }

        btnLecture.setOnClickListener(v -> {

            lectureType = edtLectureType.getText().toString().trim();
            Pattern p = Pattern.compile("[a-zA-Z]{3,}");
            Matcher m = p.matcher(lectureType);
            boolean match = m.matches();
            if (action.equals("add")) {
                if (!lectureType.isEmpty()) {
                    if (match) {
                        if (db.isLectureAlready(lectureType)) {
                            Toast.makeText(this, "Lecture already exists !", Toast.LENGTH_SHORT).show();
                        } else {
                            String insert = db.insertLecture(lectureType.toString().trim());

                            Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageLecture.this, Lecture.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Enter Lecture Type minimum 3 Characters Length",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Lecture Type", Toast.LENGTH_SHORT).show();
                }

            }

            if (action.equals("edit")) {
                if (!lectureType.isEmpty()) {
                    if (match) {
                        if (db.isLectureAlready(lectureType)) {
                            Toast.makeText(this, "Lecture already exists !", Toast.LENGTH_SHORT).show();
                        } else {
                            String result = db.updateLecture(lid, lectureType);
                            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageLecture.this, Lecture.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Enter Lecture Type minimum 5 Characters Length",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Lecture Type", Toast.LENGTH_SHORT).show();
                }
            }

            if (action.equals("delete")) {
                    String result = db.deleteLecture(lid);
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ManageLecture.this, Lecture.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
        });
    }
}