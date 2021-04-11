package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StaffPanel extends AppCompatActivity {

    private static final String LOGGED_KEY = "com.pss.myapplication.logged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_panel);


        SharedPreferences prefs = getSharedPreferences(LOGGED_KEY, MODE_PRIVATE);
        boolean login = prefs.getBoolean("isLoggedIn", false);

        if (login) {
            TextView user_id = findViewById(R.id.user_id);
            user_id.setText("User ID : " + prefs.getString("userID", ""));

            if (!prefs.getString("userType", "").equals("staff")) {
                Toast.makeText(StaffPanel.this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor =
                        getSharedPreferences(LOGGED_KEY, MODE_PRIVATE).edit();
                editor.putBoolean("isLoggedIn", false);
                editor.putString("userType", "");
                editor.apply();
                startActivity(new Intent(StaffPanel.this, MainActivity.class));
                finish();
            }
        }
    }

    public void logOut(View v) {
        Toast.makeText(StaffPanel.this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor =
                getSharedPreferences(LOGGED_KEY, MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", false);
        editor.putString("userType", "");
        editor.putString("userID", "");
        editor.apply();
        startActivity(new Intent(StaffPanel.this, MainActivity.class));
        finish();
    }

    public void changePassword(View v) {
        startActivity(new Intent(StaffPanel.this, ChangePassword.class));
    }


    public void ManageStudent(View view) {
        startActivity(new Intent(StaffPanel.this, Student.class).putExtra("UserType","professor"));
    }
}