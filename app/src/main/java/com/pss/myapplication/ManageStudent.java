package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageStudent extends AppCompatActivity {

    private dbSAMS db = new dbSAMS(this);
    private TextInputEditText tietStudentName, tietStudentConfirmPassword, tietStudentPassword, tietStudentMobileno, tietStudentPMobileno, tietStudentEmail, tietStudentPEmail;
    private TextView tvAction;
    private Button btnStudent;
    private String studentName;
    private String studentMobileno;
    private String studentEmail;
    private String studentPMobileno;
    private String studentPEmail;
    private String studentPassword;
    private String studentConfirmPassword;
    private String studentBranch;
    private String studentBatch;
    private String studentSemester;
    private String student_name;
    private String student_mobileno;
    private String student_email;
    private String student_p_mobileno;
    private String student_p_email;
    private String student_password;
    private String student_confirmPassword;
    private String student_branch;
    private String student_batch;
    private int stid;
    private int bid;
    private int btid;
    private int semester;
    private String user;

    private AutoCompleteTextView actvSelectBranch;
    private AutoCompleteTextView actvSelectBatch;
    private AutoCompleteTextView actvSelectSemester;

    private ArrayList<String> listBranch = new ArrayList<>();
    private ArrayList<String> listBatch = new ArrayList<>();
    private ArrayList<Integer> listSelect = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student);


        Cursor cursor = db.getAllBranch();

        while (cursor.moveToNext()) {
            listBranch.add(cursor.getString(cursor.getColumnIndex("branch_name")));
        }
        cursor.close();
        cursor = db.getAllBatch();

        while (cursor.moveToNext()) {
            listBatch.add(cursor.getString(cursor.getColumnIndex("batch_name")));
        }
        cursor.close();

        for (int i = 1; i <= 8; i++) {
            listSelect.add(i);
        }

        ArrayAdapter<String> autoBranch = new ArrayAdapter<>(this, R.layout.dropdown_menu, listBranch);
        ArrayAdapter<String> autoBatch = new ArrayAdapter<>(this, R.layout.dropdown_menu, listBatch);
        ArrayAdapter<Integer> autoSelect = new ArrayAdapter<>(this, R.layout.dropdown_menu, listSelect);

        actvSelectBranch = findViewById(R.id.actvSelectBranch);
        actvSelectBranch.setAdapter(autoBranch);
        actvSelectBatch = findViewById(R.id.actvSelectBatch);
        actvSelectBatch.setAdapter(autoBatch);
        actvSelectSemester = findViewById(R.id.actvSelectSemester);
        actvSelectSemester.setAdapter(autoSelect);

        tietStudentName = findViewById(R.id.tietStudentName);
        tietStudentMobileno = findViewById(R.id.tietStudentMobileno);
        tietStudentEmail = findViewById(R.id.tietStudentEmail);
        tietStudentPMobileno = findViewById(R.id.tietStudentPMobileno);
        tietStudentPEmail = findViewById(R.id.tietStudentPEmail);
        tietStudentPassword = findViewById(R.id.tietStudentPassword);
        tietStudentConfirmPassword = findViewById(R.id.tietStudentConfirmPassword);
        tvAction = findViewById(R.id.textAction);
        btnStudent = findViewById(R.id.btnStudent);


        String action = getIntent().getStringExtra("action");
        user = getIntent().getStringExtra("user");

        if (action.equals("add")) {
            tvAction.setText("Add Student");
            btnStudent.setText("Insert");
        }
        if (action.equals("edit")) {
            try {
                stid = getIntent().getIntExtra("stid", 0);
                student_name = getIntent().getStringExtra("student_name");
                student_mobileno = getIntent().getStringExtra("student_mobileno");
                student_email = getIntent().getStringExtra("student_email");
                student_p_mobileno = getIntent().getStringExtra("student_mobileno");
                student_p_email = getIntent().getStringExtra("student_p_email");
                student_password = getIntent().getStringExtra("student_password");
                student_confirmPassword = getIntent().getStringExtra("student_password");

                tietStudentName.setText(student_name);
                tietStudentMobileno.setText(student_mobileno);
                tietStudentEmail.setText(student_email);
                tietStudentPMobileno.setText(student_p_mobileno);
                tietStudentPEmail.setText(student_p_email);
                tietStudentPassword.setText(student_password);
                tietStudentConfirmPassword.setText(student_confirmPassword);

                tvAction.setText("Edit Student");
                btnStudent.setText("Update");
            } catch (Exception ex) {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
            }
        }

        btnStudent.setOnClickListener(v -> {
            studentName = tietStudentName.getText().toString().trim();
            studentMobileno = tietStudentMobileno.getText().toString().trim();
            studentEmail = tietStudentEmail.getText().toString().trim();
            studentPMobileno = tietStudentPMobileno.getText().toString().trim();
            studentPEmail = tietStudentPEmail.getText().toString().trim();
            studentConfirmPassword = tietStudentConfirmPassword.getText().toString().trim();
            studentPassword = tietStudentPassword.getText().toString().trim();
            studentBranch = actvSelectBranch.getText().toString().trim();
            studentBatch = actvSelectBatch.getText().toString().trim();
            studentSemester = actvSelectSemester.getText().toString().trim();

            Pattern p = Pattern.compile("[a-zA-Z\\s]{5,}");
            Matcher m = p.matcher(studentName);
            boolean matchName = m.matches();

            if (!studentName.isEmpty() && !studentBranch.isEmpty() && !studentBatch.isEmpty() && !studentSemester.isEmpty() && !studentPassword.isEmpty() && !studentConfirmPassword.isEmpty() && !studentEmail.isEmpty() && !studentMobileno.isEmpty() && !studentPEmail.isEmpty() && !studentPMobileno.isEmpty()) {
                if (!matchName) {
                    Toast.makeText(this, "Enter Student Name minimum 5 Characters Length",
                            Toast.LENGTH_SHORT).show();
                } else if (studentMobileno.length() != 10) {
                    Toast.makeText(this, "Enter 10 Digit Mobile Number!",
                            Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(studentEmail).matches()) {
                    Toast.makeText(this, "Enter Valid Email Address!",
                            Toast.LENGTH_SHORT).show();
                } else if (studentPMobileno.length() != 10) {
                    Toast.makeText(this, "Enter 10 Digit Parent Mobile Number!",
                            Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(studentPEmail).matches()) {
                    Toast.makeText(this, "Enter Valid Parent Email Address!",
                            Toast.LENGTH_SHORT).show();
                } else if (!studentPassword.equals(studentConfirmPassword)) {
                    Toast.makeText(this, "Confirm Password Doesn't Match!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (action.equals("add")) {
                        if (db.isStudentAlready(studentEmail, studentMobileno)) {
                            Toast.makeText(this, "Student already exists !",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            bid = Integer.parseInt(db.getBranchID(studentBranch));
                            btid = Integer.parseInt(db.getBatchID(studentBatch));
                            semester = Integer.parseInt(studentSemester);
                            String insert = db.insertStudent(studentName, studentEmail,
                                    studentMobileno, studentPEmail, studentPMobileno,
                                    studentPassword, btid, bid, semester);

                            Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageStudent.this, Student.class).putExtra(
                                    "UserType",user);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    }
                    if (action.equals("edit")) {
                        if (db.studentCount(studentEmail, studentMobileno) > 1) {
                            Toast.makeText(this, "Student already exists !",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            bid = Integer.parseInt(db.getBranchID(studentBranch));
                            btid = Integer.parseInt(db.getBatchID(studentBatch));
                            semester = Integer.parseInt(studentSemester);
                            String update = db.updateStudent(stid, studentName,
                                    studentEmail, studentMobileno,studentPEmail, studentPMobileno,
                                    studentPassword,
                                    btid,bid,semester);
                            Toast.makeText(this, update,
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageStudent.this, Student.class).putExtra(
                                    "UserType",user);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Fill All The Blanks", Toast.LENGTH_SHORT).show();
            }
        });
    }
}