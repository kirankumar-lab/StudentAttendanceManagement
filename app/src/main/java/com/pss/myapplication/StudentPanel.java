package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StudentPanel extends AppCompatActivity {
    private static final String LOGGED_KEY = "com.pss.myapplication.logged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);
    }

    public void logOut(View v) {
        Toast.makeText(StudentPanel.this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor =
                getSharedPreferences(LOGGED_KEY, MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", false);
        editor.putString("userType", "");
        editor.putString("userID", "");
        editor.apply();
        startActivity(new Intent(StudentPanel.this, MainActivity.class));
        finish();
    }
}