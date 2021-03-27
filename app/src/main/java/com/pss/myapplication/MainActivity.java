package com.pss.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private static final String LOGGED_KEY = "com.pss.myapplication.logged";
    private CardView admin, staff, student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(LOGGED_KEY, MODE_PRIVATE);
        Boolean login = prefs.getBoolean("isLoggedIn",false);

        if(login)
        {
            if(prefs.getString("userType","").equals("admin"))
            {
                startActivity(new Intent(MainActivity.this, AdminPanel.class));
                finish();
            }
            if(prefs.getString("userType","").equals("staff"))
            {
                startActivity(new Intent(MainActivity.this, StaffPanel.class));
                finish();
            }
            if(prefs.getString("userType","").equals("student"))
            {
                startActivity(new Intent(MainActivity.this, StudentPanel.class));
                finish();
            }
        }

        admin = findViewById(R.id.admin);
        staff = findViewById(R.id.staff);
        student = findViewById(R.id.student);

        admin.setOnClickListener(v -> login("admin"));

        staff.setOnClickListener(v -> login("staff"));

        student.setOnClickListener(v -> login("student"));
    }

    private void login(String user)
    {
        Intent i = new Intent(MainActivity.this, Login.class);
        i.putExtra("user", user);
        startActivity(i);
        finish();
    }
}