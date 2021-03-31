package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageProfessor extends AppCompatActivity {

    private dbSAMS db = new dbSAMS(this);
    private TextInputEditText tietProfessorName,tietProfessorConfirmPassword,tietProfessorPassword,tietProfessorMobileno,
            tietProfessorEmail;
    private TextView tvAction;
    private Button btnProfessor;
    private String professorName;
    private String professorMobileno;
    private String professorEmail;
    private String professorPassword;
    private String professorConfirmPassword;
    private String professorBranch;
    private String professor_name;
    private String professor_mobile;
    private String professor_email;
    private String professor_password;
    private String professor_confirmPassword;
    private String professor_branch;
    private int sid;
    private int bid;

    private AutoCompleteTextView actvSelectBranch;

    private ArrayList<String> listBranch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_professor);

        Cursor cursor = db.getAllProfessor();

        while (cursor.moveToNext()) {
            listBranch.add(cursor.getString(cursor.getColumnIndex("branch_name")));
        }
        cursor.close();

        ArrayAdapter<String> autoBranch = new ArrayAdapter<>(this, R.layout.dropdown_menu, listBranch);

        actvSelectBranch = findViewById(R.id.actvSelectBranch);
        actvSelectBranch.setAdapter(autoBranch);

        tietProfessorName = findViewById(R.id.tietProfessorName);
        tietProfessorMobileno = findViewById(R.id.tietProfessorMobileno);
        tietProfessorEmail = findViewById(R.id.tietProfessorEmail);
        tietProfessorPassword = findViewById(R.id.tietProfessorPassword);
        tietProfessorConfirmPassword = findViewById(R.id.tietProfessorConfirmPassword);
        tvAction = findViewById(R.id.textAction);
        btnProfessor = findViewById(R.id.btnProfessor);

        String action = getIntent().getStringExtra("action");

        if (action.equals("add")) {
            tvAction.setText("Add Professor");
            btnProfessor.setText("Insert");
        }
        if (action.equals("edit")) {
            try {
                sid = getIntent().getIntExtra("sid", 0);
                professor_name = getIntent().getStringExtra("professor_name");
                professor_name = getIntent().getStringExtra("professor_mobileno");
                professor_name = getIntent().getStringExtra("professor_email");
                tvAction.setText("Edit Professor");
                btnProfessor.setText("Update");
            } catch (Exception ex) {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
            }
        }

        btnProfessor.setOnClickListener(v -> {
            professorName = tietProfessorName.getText().toString().trim();
            professorMobileno = tietProfessorName.getText().toString().trim();
            professorEmail = tietProfessorName.getText().toString().trim();
            professorConfirmPassword = tietProfessorName.getText().toString().trim();
            professorPassword = tietProfessorName.getText().toString().trim();
            professorBranch = actvSelectBranch.getText().toString().trim();

            Pattern p = Pattern.compile("[a-zA-Z\\s]{10,}");
            Matcher m = p.matcher(professorName);
            boolean match = m.matches();

            if (action.equals("add")) {
                if (!professorName.isEmpty() && !professorBranch.isEmpty() && !professorPassword.isEmpty() && !professorConfirmPassword.isEmpty() && !professorEmail.isEmpty() && !professorMobileno.isEmpty()) {
                    if (match) {
                        if (db.isSubjectAlready(subjectName)) {
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
                        if (db.subjectCount(subjectName) >1) {
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
    }
}