package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ForgotPassword extends AppCompatActivity {

    private TextInputEditText etEmail;
    private dbSAMS db;
    private String user;
    private String email;
    private String subject;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);

        db = new dbSAMS(this);

        user = getIntent().getStringExtra("user");
        subject = "Student Attendance System";
        message = "";
    }

    public void sendPass(View v)
    {
        email = etEmail.getText().toString().trim();
        ArrayList<String> users = new ArrayList<>();
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(ForgotPassword.this, "Invalid Email Address", Toast.LENGTH_LONG).show();
        }
        else
        {
            // for admin
            if (user.equals("admin"))
            {
                Cursor r = db.getAllAdmin();

                while (r.moveToNext()) {
                    users.add(r.getString(2));
                }

                if(users.contains(email))
                {
                    Toast.makeText(ForgotPassword.this, "Password sent to : " + email, Toast.LENGTH_LONG).show();
                    String password = db.getPasswordForAdmin(email);
                    message = "To : " + email + "\n\nYour Current Password : ";
                    message = message + password;
                    JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, message);
                    javaMailAPI.execute();
                }
                else
                {
                    Toast.makeText(ForgotPassword.this, "Account not found link to this email address", Toast.LENGTH_LONG).show();
                }
            }

            // for professor
            if (user.equals("staff"))
            {
                Cursor r = db.getAllProfessor();

                while (r.moveToNext()) {
                    users.add(r.getString(2));
                }

                if(users.contains(email))
                {
                    Toast.makeText(ForgotPassword.this, "Password sent to : " + email, Toast.LENGTH_LONG).show();
                    String password = db.getPasswordForProfessor(email);
                    message = "To : " + email + "\n\nYour Current Password : ";
                    message = message + password;
                    JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, message);
                    javaMailAPI.execute();
                }
                else
                {
                    Toast.makeText(ForgotPassword.this, "Account not found link to this email address", Toast.LENGTH_LONG).show();
                }
            }


            // for student
            if (user.equals("student"))
            {
                Cursor r = db.getAllStudent();

                while (r.moveToNext()) {
                    users.add(r.getString(2));
                }

                if(users.contains(email))
                {
                    Toast.makeText(ForgotPassword.this, "Password sent to : " + email, Toast.LENGTH_LONG).show();
                    String password = db.getPasswordForStudent(email);
                    message = "To : " + email + "\n\nYour Current Password : ";
                    message = message + password;
                    JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, message);
                    javaMailAPI.execute();
                }
                else
                {
                    Toast.makeText(ForgotPassword.this, "Account not found link to this email address", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}