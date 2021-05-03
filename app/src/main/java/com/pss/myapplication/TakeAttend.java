package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TakeAttend extends AppCompatActivity {
private int tsid,slot;
private String date,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attend);

        tsid=getIntent().getIntExtra("tsid",0);
        slot=getIntent().getIntExtra("slot",0);
        date=getIntent().getStringExtra("date");
        description=getIntent().getStringExtra("description");


    }
}