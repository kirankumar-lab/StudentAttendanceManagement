package com.pss.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;


import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ofPattern;

public class TakeAttendance extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextInputLayout tilSelectDate, tilSelectSlot, tilSelectSubject,
            tilSelectLecture, tilDescription;
    private AutoCompleteTextView actvSelectDate, actvSelectSlot, actvSelectBatch, actvSelectSubject,
            actvSelectLecture;
    private TextInputEditText tietDescription;
    private String selectedDate = null;
    private SimpleDateFormat simpleDate;
    private dbSAMS db;
    private String sid;
    private ArrayList<String> listBatch, listSubject, listLecture;
    private ArrayList<Integer> listSlot;
    private ArrayAdapter adpSlot, adpBatch, adpSubject, adpLecture;

    private int btid;
    private int bid;
    private int lid;
    private int sbid;
    private int slot;
    private String date;
    private String decription;

    int tsid = db.getTakeSubjectID(btid, bid, lid, sbid, Integer.parseInt(db.getProfessorId(sid)));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        sid = getIntent().getStringExtra("prof_id");

        db = new dbSAMS(this);

        //Declare date formate
        simpleDate = new SimpleDateFormat(getString(R.string.dateFormate));

        //Declare All fields
        tilSelectDate = findViewById(R.id.tilSelectDate);
        tilSelectSlot = findViewById(R.id.tilSelectSlot);
        tilSelectSubject = findViewById(R.id.tilSelectSubject);
        tilSelectLecture = findViewById(R.id.tilSelectLecture);
        tilDescription = findViewById(R.id.tilDescription);

        tietDescription = findViewById(R.id.tietDecription);

        actvSelectDate = findViewById(R.id.actvSelectDate);
        actvSelectBatch = findViewById(R.id.actvSelectBatch);
        actvSelectSubject = findViewById(R.id.actvSelectSubject);
        actvSelectLecture = findViewById(R.id.actvSelectLecture);
        actvSelectSlot = findViewById(R.id.actvSelectSlot);


        listSlot = new ArrayList<>();
        listBatch = new ArrayList<>();
        listSubject = new ArrayList<>();
        listLecture = new ArrayList<>();

        Cursor cursor = db.getTakeSubjectBySID(sid);
        while (cursor.moveToNext()) {
            listBatch.add(db.getBatchName(Integer.parseInt(cursor.getString(cursor.getColumnIndex("btid")))));
        }
        cursor.close();
        adpBatch = new ArrayAdapter(this, R.layout.dropdown_menu, listBatch);
        actvSelectBatch.setAdapter(adpBatch);
        actvSelectBatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tilSelectLecture.setEnabled(true);
            }
        });


        cursor = db.getAllLecture();
        while (cursor.moveToNext()) {
            listLecture.add(cursor.getString(cursor.getColumnIndex("lecture_type")));
        }
        cursor.close();
        adpLecture = new ArrayAdapter(this, R.layout.dropdown_menu, listLecture);
        actvSelectLecture.setAdapter(adpLecture);
        actvSelectLecture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Cursor cursor =
                            db.getTakeSubjectByLecture(parent.getItemAtPosition(position).toString(), actvSelectBatch.getText().toString(), sid);
                    actvSelectSubject.setText("");
                    listSubject.clear();
                    while (cursor.moveToNext()) {
                        listSubject.add(db.getTakeSubjectName(cursor.getString(cursor.getColumnIndex("sbid"))));
                    }
                    cursor.close();
                    tilSelectSubject.setEnabled(true);
                    adpSubject = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu, listSubject);
                    actvSelectSubject.setAdapter(adpSubject);
                } catch (Exception ex) {
                    return;
                }
            }
        });


        actvSelectSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tilSelectSlot.setEnabled(true);
                tilSelectDate.setEnabled(true);
                tilDescription.setEnabled(true);
            }
        });


        for (int slot = 1; slot <= 6; slot++) {
            listSlot.add(slot);
        }
        actvSelectSlot.setText("1");
        adpSlot = new ArrayAdapter(this, R.layout.dropdown_menu, listSlot);
        actvSelectSlot.setAdapter(adpSlot);


        actvSelectDate.setText(simpleDate.format(Calendar.getInstance().getTime()));


        //On Select Date
        tilSelectDate.setEndIconOnClickListener(v -> {
            selectDate(v);
        });
        tilSelectDate.setOnClickListener(v -> {
            selectDate(v);
        });
        actvSelectDate.setOnClickListener(v -> {
            selectDate(v);
        });
    }

    public void selectDate(View view) {
        try {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getFragmentManager(), "date picker");
            //String date = datePicker.getSelection().toString();
            //Toast.makeText(this,date,Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    /// Method to format selected Date /////////////////////////////////////////////////////////////////
    @Override
    public void onDateSet(DatePicker view, int year, int month, int date) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, date);
        selectedDate = simpleDate.format(c.getTime());
        actvSelectDate.setText(selectedDate);
    }

    //First Layout Take Attendance Method
    public void TakeAttendance(View view) {
        try {
            boolean isSlot = actvSelectSlot != null && actvSelectSlot.getText().toString().isEmpty();
            boolean isSubject = actvSelectSubject != null && actvSelectSubject.getText().toString().isEmpty();
            boolean isBatch = actvSelectBatch != null && actvSelectBatch.getText().toString().isEmpty();
            boolean isLecture = actvSelectLecture != null && actvSelectLecture.getText().toString().isEmpty();
            boolean isDate = actvSelectDate != null && actvSelectDate.getText().toString().isEmpty();
            boolean isDecription = tietDescription != null && tietDescription.getText().toString().isEmpty();

            if (!isBatch && !isLecture && !isSubject && !isSlot && !isDate && !isDecription) {
                btid = Integer.parseInt(db.getBatchID(actvSelectBatch.getText().toString()));
                bid = db.getBranchIDBySID(sid);
                lid = Integer.parseInt(db.getLectureID(actvSelectLecture.getText().toString()));
                sbid = Integer.parseInt(db.getSubjectID(actvSelectSubject.getText().toString()));

                slot = Integer.parseInt(actvSelectSlot.getText().toString());
                date = actvSelectDate.getText().toString();
                decription = tietDescription.getText().toString();

                int tsid = db.getTakeSubjectID(btid, bid, lid, sbid, Integer.parseInt(db.getProfessorId(sid)));
                setContentView(R.layout.activity_take_attendance_p);
            } else {
                Toast.makeText(this, "Please Select Or Fill All The Fields.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            System.console().writer().print(ex.toString());
        }
    }
}