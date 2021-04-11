package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TakeSubject extends AppCompatActivity {

    private String prof_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_subject);

        prof_id = getIntent().getStringExtra("prof_id");

    }

    public void addSubject(View v) {
        Intent i  = new Intent(TakeSubject.this, ManageTakeSubject.class);
        i.putExtra("prof_id",prof_id);
        startActivity(i);
    }
}