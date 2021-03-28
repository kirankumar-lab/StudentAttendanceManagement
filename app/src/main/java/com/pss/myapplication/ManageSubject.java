package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class ManageSubject extends AppCompatActivity {

    private dbSAMS db;
    private AutoCompleteTextView actvSelectBranch;
    private AutoCompleteTextView actvSelectSemester;

    private ArrayList<String> listBranch = new ArrayList<>();
    private ArrayList<Integer> listSelect = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_subject);
            db = new dbSAMS(this);

            Cursor cursor = db.getAllBranch();

            while (cursor.moveToNext()) {
                listBranch.add(cursor.getString(cursor.getColumnIndex("branch_name")));
            }
            cursor.close();

            for (int i=1; i<=8; i++) {
                listSelect.add(i);
            }

            ArrayAdapter<String> autoBranch = new ArrayAdapter<>(this, R.layout.dropdown_menu, listBranch);
            ArrayAdapter<Integer> autoSelect = new ArrayAdapter<>(this, R.layout.dropdown_menu,listSelect);

            actvSelectBranch = findViewById(R.id.actvSelectBranch);
            actvSelectBranch.setAdapter(autoBranch);
            actvSelectSemester = findViewById(R.id.actvSelectSemester);
            actvSelectSemester.setAdapter(autoSelect);
    }
}