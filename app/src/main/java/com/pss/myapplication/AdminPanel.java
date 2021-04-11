package com.pss.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdminPanel extends AppCompatActivity {
    private static final String LOGGED_KEY = "com.pss.myapplication.logged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        SharedPreferences prefs = getSharedPreferences(LOGGED_KEY, MODE_PRIVATE);
        boolean login = prefs.getBoolean("isLoggedIn", false);

        if (login) {
            TextView user_id = findViewById(R.id.user_id);
            user_id.setText("User ID : " + prefs.getString("userID", ""));

            if (!prefs.getString("userType", "").equals("admin")) {
                Toast.makeText(AdminPanel.this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor =
                        getSharedPreferences(LOGGED_KEY, MODE_PRIVATE).edit();
                editor.putBoolean("isLoggedIn", false);
                editor.putString("userType", "");
                editor.apply();
                startActivity(new Intent(AdminPanel.this, MainActivity.class));
                finish();
            }
        }

    }

    public void logOut(View v) {
        Toast.makeText(AdminPanel.this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor =
                getSharedPreferences(LOGGED_KEY, MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", false);
        editor.putString("userType", "");
        editor.putString("userID", "");
        editor.apply();
        startActivity(new Intent(AdminPanel.this, MainActivity.class));
        finish();
    }

    public void changePassword(View v) {
        startActivity(new Intent(AdminPanel.this, ChangePassword.class));
    }

    public void batch(View v) {
        startActivity(new Intent(AdminPanel.this, Batch.class));
    }

    public void branch(View v) {
        startActivity(new Intent(AdminPanel.this, Branch.class));
    }

    public void subject(View v) {
        startActivity(new Intent(AdminPanel.this, Subject.class));
    }

    public void lecture(View v) {
        startActivity(new Intent(AdminPanel.this, Lecture.class));
    }

    public void professor(View v) {
        startActivity(new Intent(AdminPanel.this, Professor.class));
    }

    public void student(View v) {
        startActivity(new Intent(AdminPanel.this, Student.class).putExtra("UserType","admin"));
    }

    public void attendance(View v) {
        startActivity(new Intent(AdminPanel.this, Attendance.class));
    }
/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(AdminPanel.this, "Login out Successfully", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor =
                getSharedPreferences(LOGGED_KEY, MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn",false);
        editor.putString("userType","");
        editor.commit();
        Intent i = new Intent(AdminPanel.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }*/
}