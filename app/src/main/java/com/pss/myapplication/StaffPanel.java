package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StaffPanel extends AppCompatActivity {

    private static final String LOGGED_KEY = "com.pss.myapplication.logged";
    private String prof_id,prof_branch_id;
    private dbSAMS db = new dbSAMS(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_panel);

        SharedPreferences prefs = getSharedPreferences(LOGGED_KEY, MODE_PRIVATE);
        boolean login = prefs.getBoolean("isLoggedIn", false);
        prof_id = prefs.getString("userID", "");

        // for fetch branch id of logged professor
        Cursor cursor = db.getProfDetailsById(prof_id);
        while (cursor.moveToNext()) {
            prof_branch_id = cursor.getString(cursor.getColumnIndex("bid"));
        }
        cursor.close();






        if (login) {
            TextView user_id = findViewById(R.id.user_id);
            user_id.setText("User ID : " + prof_id);

            TextView branch_id = findViewById(R.id.branch_id);
            branch_id.setText("Branch Id: " + prof_branch_id);

            if (!prefs.getString("userType", "").equals("staff")) {
                Toast.makeText(StaffPanel.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
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

    public void takeSubject(View v) {
        Intent i = new Intent(StaffPanel.this, TakeSubject.class);
        i.putExtra("prof_id", prof_id);
        startActivity(i);
    }

    public void ManageStudent(View view) {
        startActivity(new Intent(StaffPanel.this, Student.class).putExtra("UserType", "staff"));
    }

    public void TakeAttendance(View view) {
        startActivity(new Intent(StaffPanel.this, TakeAttendance.class).putExtra("prof_id", prof_id));
    }

    public void EditAttendance(View view) {
        startActivity(new Intent(StaffPanel.this, ManageAttendance.class).putExtra("prof_id", prof_id));
    }
}