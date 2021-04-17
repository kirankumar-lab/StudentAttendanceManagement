package com.pss.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageProfessor extends AppCompatActivity {

    private dbSAMS db = new dbSAMS(this);
    private TextInputEditText tietProfessorName, tietProfessorConfirmPassword, tietProfessorPassword, tietProfessorMobileno,
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
    private String professor_mobileno;
    private String professor_email;
    private String professor_password;
    private String professor_confirmPassword;
    private String professor_branch;
    private int sid;
    private int bid;

    private AutoCompleteTextView actvSelectBranch;

    private ArrayList<String> listBranch = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_professor);

        Cursor cursor = db.getAllBranch();

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
                professor_mobileno = getIntent().getStringExtra("professor_mobileno");
                professor_email = getIntent().getStringExtra("professor_email");
                professor_password = getIntent().getStringExtra("professor_password");
                professor_confirmPassword = getIntent().getStringExtra("professor_password");

                tietProfessorName.setText(professor_name);
                tietProfessorMobileno.setText(professor_mobileno);
                tietProfessorEmail.setText(professor_email);
                tietProfessorPassword.setText(professor_password);
                tietProfessorConfirmPassword.setText(professor_confirmPassword);

                actvSelectBranch.setText(getIntent().getStringExtra("bid"),false);

                tvAction.setText("Edit Professor");
                btnProfessor.setText("Update");
            } catch (Exception ex) {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
            }
        }

        btnProfessor.setOnClickListener(v -> {
            professorName = tietProfessorName.getText().toString().trim();
            professorMobileno = tietProfessorMobileno.getText().toString().trim();
            professorEmail = tietProfessorEmail.getText().toString().trim();
            professorConfirmPassword = tietProfessorConfirmPassword.getText().toString().trim();
            professorPassword = tietProfessorPassword.getText().toString().trim();
            professorBranch = actvSelectBranch.getText().toString().trim();

            Pattern p = Pattern.compile("[a-zA-Z\\s]{5,}");
            Matcher m = p.matcher(professorName);
            boolean matchName = m.matches();

            if (!professorName.isEmpty() && !professorBranch.isEmpty() && !professorPassword.isEmpty() && !professorConfirmPassword.isEmpty() && !professorEmail.isEmpty() && !professorMobileno.isEmpty()) {
                if (!matchName) {
                    Toast.makeText(this, "Enter Professor Name minimum 5 Characters Length",
                            Toast.LENGTH_SHORT).show();
                } else if (professorMobileno.length() != 10) {
                    Toast.makeText(this, "Enter 10 Digit Mobile Number!",
                            Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(professorEmail).matches()) {
                    Toast.makeText(this, "Enter Valid Email Address!",
                            Toast.LENGTH_SHORT).show();
                } else if (!professorPassword.equals(professorConfirmPassword)) {
                    Toast.makeText(this, "Confirm Password Doesn't Match!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (action.equals("add")) {
                        if (db.isProfessorAlready(professorEmail)) {
                            Toast.makeText(this, "Professor already exists !",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            bid = Integer.parseInt(db.getBranchID(professorBranch));
                            String insert = db.insertProfessor(professorName,
                                    professorEmail, professorPassword,
                                    professorMobileno, bid);

                            Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageProfessor.this, Professor.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    }
                    if (action.equals("edit")) {
                        if (db.professorCount(professorEmail) > 1) {
                            Toast.makeText(this, "Professor already exists !",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            bid = Integer.parseInt(db.getBranchID(professorBranch));
                            String update = db.updateProfessor(sid, professorName,
                                    professorEmail, professorPassword, professorMobileno,
                                    bid);
                            Toast.makeText(this, update,
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageProfessor.this, Professor.class);
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