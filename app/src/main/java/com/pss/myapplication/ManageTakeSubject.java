package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ManageTakeSubject extends AppCompatActivity  {

    private dbSAMS db = new dbSAMS(this);

    private String prof_id,prof_email_id,prof_branch_id;
    private int btid,sbid,lid;

    private AutoCompleteTextView actvBatch,actvSubject,actvLectureType;
    private TextInputLayout tilSubject;

    private ArrayList<String> fetchedBatch = new ArrayList<>();
    private ArrayList<String> fetchedSubject = new ArrayList<>();
    private ArrayList<String> fetchedLectureType = new ArrayList<>();

    private ArrayAdapter<String> fetchedBatchAdap,fetchedSubjectAdap,fetchedLectureTypeAdap;

    private String selectedBatch="",selectedSubject="",selectedLectureType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_take_subject);

        prof_email_id = getIntent().getStringExtra("prof_id");

        actvBatch = findViewById(R.id.actvBatch);
        actvSubject = findViewById(R.id.actvSubject);
        actvLectureType = findViewById(R.id.actvLectureType);

        tilSubject = findViewById(R.id.tilSubject);

        // for fetch branch id of logged professor
        Cursor cursor = db.getProfDetailsById(prof_email_id);
        while (cursor.moveToNext()) {
            prof_branch_id = cursor.getString(cursor.getColumnIndex("bid"));
            prof_id = cursor.getString(cursor.getColumnIndex("sid"));
        }
        cursor.close();


        // for batch dropdown //////////////////////////////////////////////////////////////////////
        Cursor cursor1 = db.getAllBatch();
        while (cursor1.moveToNext()) {
            fetchedBatch.add(cursor1.getString(cursor1.getColumnIndex("batch_name")));
        }
        cursor1.close();

        fetchedBatchAdap = new ArrayAdapter<>(this, R.layout.dropdown_menu, fetchedBatch);
        actvBatch.setAdapter(fetchedBatchAdap);
        actvBatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBatch = parent.getItemAtPosition(position).toString();
                btid = Integer.parseInt(db.getBatchID(selectedBatch));
            }
        });


        // for lecture type dropdown //////////////////////////////////////////////////////////////////////
        Cursor cursor2 = db.getAllSubjectOfBranch(prof_branch_id);
        while (cursor2.moveToNext()) {
            fetchedSubject.add(cursor2.getString(cursor2.getColumnIndex("subject_name")));
        }
        cursor2.close();

        fetchedSubjectAdap = new ArrayAdapter<>(this, R.layout.dropdown_menu, fetchedSubject);
        actvSubject.setAdapter(fetchedSubjectAdap);
        actvSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = parent.getItemAtPosition(position).toString();
                sbid = Integer.parseInt(db.getSubjectID(selectedSubject));
            }
        });


        // for lecture type dropdown //////////////////////////////////////////////////////////////////////
        Cursor cursor3 = db.getAllLecture();
        while (cursor3.moveToNext()) {
            fetchedLectureType.add(cursor3.getString(cursor3.getColumnIndex("lecture_type")));
        }
        cursor3.close();

        fetchedLectureTypeAdap = new ArrayAdapter<>(this, R.layout.dropdown_menu, fetchedLectureType);
        actvLectureType.setAdapter(fetchedLectureTypeAdap);
        actvLectureType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedLectureType = parent.getItemAtPosition(position).toString();
                lid = Integer.parseInt(db.getLectureID(selectedLectureType));
            }
        });


    }

    public void takeSubject(View v){

        String message = db.insertTakeSubject(Integer.parseInt(prof_branch_id),btid,Integer.parseInt(prof_id),sbid,lid);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ManageTakeSubject.this, TakeSubject.class);
        i.putExtra("prof_id",prof_email_id);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

}