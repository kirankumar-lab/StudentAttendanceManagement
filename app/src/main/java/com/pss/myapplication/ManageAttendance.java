package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ManageAttendance extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,AdapterStudentAttendanceList.ItemClicked {
    private TextInputLayout tilSelectDate, tilSelectSlot, tilSelectSubject,
            tilSelectLecture,tilSelectSemester;
    private AutoCompleteTextView actvSelectDate, actvSelectSlot, actvSelectBatch,
            actvSelectSubject,actvSelectSemester,
            actvSelectLecture;
    private String selectedDate = null;
    private SimpleDateFormat simpleDate;
    private dbSAMS db;
    private String sid;
    private ArrayList<String> listBatch, listSubject, listLecture;
    private ArrayList<Integer> listSlot,listSemester;
    private ArrayAdapter adpSlot, adpBatch, adpSubject,adpSemester, adpLecture;

    private MaterialToolbar toolbar;
    private CheckBox checkBox;
    private TextView student_id;
    private boolean isCheck = false;
    private int total = 0;
    private int adid;

    private TextView tvTitle;
    private ScrollView scrollView;
    private CoordinatorLayout coordinatorLayout;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListStudentAttendance> data;

    private boolean backCount = false;

    private int btid;
    private int bid;
    private int lid;
    private int sbid;
    private int sem;
    private int slot;
    private String date;

    private int tsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_attendance);

        tvTitle = findViewById(R.id.take_attendance_title);
        scrollView = findViewById(R.id.fetch);
        coordinatorLayout = findViewById(R.id.attendanceData);


        sid = getIntent().getStringExtra("prof_id");

        db = new dbSAMS(this);


        recyclerView = findViewById(R.id.student_list);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        data = new ArrayList<>();

        //Declare date formate
        simpleDate = new SimpleDateFormat(getString(R.string.dateFormate));

        //Declare All fields
        tilSelectDate = findViewById(R.id.tilSelectDate);
        tilSelectSlot = findViewById(R.id.tilSelectSlot);
        tilSelectSubject = findViewById(R.id.tilSelectSubject);
        tilSelectLecture = findViewById(R.id.tilSelectLecture);
        tilSelectSemester = findViewById(R.id.tilSemester);

        actvSelectDate = findViewById(R.id.actvSelectDate);
        actvSelectBatch = findViewById(R.id.actvSelectBatch);
        actvSelectSubject = findViewById(R.id.actvSelectSubject);
        actvSelectSemester = findViewById(R.id.actvSelectSemester);
        actvSelectLecture = findViewById(R.id.actvSelectLecture);
        actvSelectSlot = findViewById(R.id.actvSelectSlot);


        listSlot = new ArrayList<>();
        listBatch = new ArrayList<>();
        listSubject = new ArrayList<>();
        listLecture = new ArrayList<>();
        listSemester = new ArrayList<>();

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
                tilSelectSemester.setEnabled(false);
                listSemester.clear();
                adpSemester = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu, listSemester);
                actvSelectSemester.setAdapter(adpSemester);

                tilSelectLecture.setEnabled(false);
                listLecture.clear();
                adpLecture = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu,
                        listLecture);
                actvSelectLecture.setAdapter(adpLecture);

                tilSelectSubject.setEnabled(false);
                listSubject.clear();
                adpSubject = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu,
                        listSubject);
                actvSelectSubject.setAdapter(adpSubject);

                tilSelectSlot.setEnabled(false);
                listSlot.clear();
                adpSlot = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu,
                        listSlot);
                actvSelectSlot.setAdapter(adpSlot);

                tilSelectDate.setEnabled(false);
                actvSelectDate.setText("");

                tilSelectSemester.setEnabled(true);
                for (int sem = 1; sem <= 8; sem++) {
                    listSemester.add(sem);
                }
                adpSemester = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu, listSemester);
                actvSelectSemester.setAdapter(adpSemester);
            }
        });

        actvSelectSemester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = db.getAllLecture();

                tilSelectLecture.setEnabled(false);
                listLecture.clear();
                //adpLecture.clear();
                adpLecture = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu,
                        listLecture);
                actvSelectLecture.setAdapter(adpLecture);

                tilSelectSubject.setEnabled(false);
                listSubject.clear();
                adpSubject = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu,
                        listSubject);
                actvSelectSubject.setAdapter(adpSubject);

                tilSelectSlot.setEnabled(false);
                listSlot.clear();
                adpSlot = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu,
                        listSlot);
                actvSelectSlot.setAdapter(adpSlot);

                tilSelectDate.setEnabled(false);
                actvSelectDate.setText("");

                while (cursor.moveToNext()) {
                    listLecture.add(cursor.getString(cursor.getColumnIndex("lecture_type")));
                }
                cursor.close();
                tilSelectLecture.setEnabled(true);
                adpLecture = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu, listLecture);
                actvSelectLecture.setAdapter(adpLecture);
            }
        });

        actvSelectLecture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tilSelectSubject.setEnabled(false);
                listSubject.clear();
                adpSubject = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu,
                        listSubject);
                actvSelectSubject.setAdapter(adpSubject);

                tilSelectSlot.setEnabled(false);
                listSlot.clear();
                adpSlot = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu,
                        listSlot);
                actvSelectSlot.setAdapter(adpSlot);

                tilSelectDate.setEnabled(false);
                actvSelectDate.setText("");

                try {
                    Cursor cursor =
                            db.getTakeSubjectByLecture(parent.getItemAtPosition(position).toString(), actvSelectBatch.getText().toString(), sid,Integer.parseInt(actvSelectSemester.getText().toString()));
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

                tilSelectSlot.setEnabled(false);
                listSlot.clear();
                adpSlot = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu,
                        listSlot);
                actvSelectSlot.setAdapter(adpSlot);

                tilSelectDate.setEnabled(false);
                actvSelectDate.setText("");

                for (int slot = 1; slot <= 6; slot++) {
                    listSlot.add(slot);
                }
                adpSlot = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_menu, listSlot);
                actvSelectSlot.setAdapter(adpSlot);
                tilSelectSlot.setEnabled(true);
            }
        });

        actvSelectSlot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tilSelectDate.setEnabled(false);
                actvSelectDate.setText("");

                tilSelectDate.setEnabled(true);
                actvSelectDate.setText(simpleDate.format(Calendar.getInstance().getTime()));
            }
        });


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

        toolbar = findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener (v->{
            if(backCount){
                coordinatorLayout.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                backCount=false;
            }
            else{
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener (item -> {
            if(item.getItemId() == R.id.selectAll){
                for (int i=0; i< total; i++)
                {
                    checkBox = recyclerView.getChildAt(i).findViewById(R.id.isPresent);
                    if(isCheck)
                    {
                        checkBox.setChecked(false);
                    }
                    else
                    {
                        checkBox.setChecked(true);
                    }
                }
                if (isCheck)
                {
                    isCheck = false;
                }
                else{
                    isCheck = true;
                }
            }
            if (item.getItemId() == R.id.saveAll) {
                if(adid != 0)
                {
                    int count=0;
                    boolean updated;
                    for (int i = 0; i < total; i++) {
                        checkBox = recyclerView.getChildAt(i).findViewById(R.id.isPresent);
                        student_id = recyclerView.getChildAt(i).findViewById(R.id.student_id);
                        updated = db.updateAttendance(adid,
                                Integer.parseInt(student_id.getText().toString()),checkBox.isChecked());
                        if(updated){
                            count++;
                        }
                        else
                        {
                            db.deleteAttend(adid);
                            count = 0;
                            break;
                        }
                    }
                    if(count==total)
                    {
                        Toast.makeText(this,"Attendance Taked Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(this,"Attendance Taked Failed",Toast.LENGTH_SHORT).show();
                        if(backCount){
                            coordinatorLayout.setVisibility(View.GONE);
                            tvTitle.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                            backCount=false;
                        }
                        else{
                            finish();
                        }
                    }
                }
            }
            return true;
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
            boolean isSemester = actvSelectSemester != null && actvSelectSemester.getText().toString().isEmpty();

            if (!isBatch && !isLecture && !isSubject && !isSlot && !isDate && !isSemester) {
                btid = Integer.parseInt(db.getBatchID(actvSelectBatch.getText().toString()));
                bid = db.getBranchIDBySID(sid);
                lid = Integer.parseInt(db.getLectureID(actvSelectLecture.getText().toString()));
                sbid = Integer.parseInt(db.getSubjectID(actvSelectSubject.getText().toString()));
                sem = Integer.parseInt(actvSelectSemester.getText().toString());
                slot = Integer.parseInt(actvSelectSlot.getText().toString());
                date = actvSelectDate.getText().toString();

                tsid = db.getTakeSubjectID(btid, bid, lid, sbid, Integer.parseInt(db.getProfessorId(sid)));

                if(!db.checkAttendanceDetails(tsid,slot,sem,date,btid, bid, lid, sbid))
                {
                    backCount = true;
                    scrollView.setVisibility(View.GONE);
                    tvTitle.setVisibility(View.GONE);
                    coordinatorLayout.setVisibility(View.VISIBLE);

                    adid   = db.getADID(tsid,slot,sem,date);

                    Cursor cursor = db.getAttendance(adid);
                    int no = 1;
                    while (cursor.moveToNext()) {
                        data.add(new ListStudentAttendance(cursor.getInt(0),
                                db.getStudentName(cursor.getInt(0)),
                                cursor.getInt(1) == 1
                                ,no));
                        no++;
                    }
                    cursor.close();
                    myAdapter = new AdapterStudentAttendanceList(this, data);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                    total = myAdapter.getItemCount();
                }
                else{
                    Toast.makeText(this, "Records Not Found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please Select Or Fill All The Fields.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            System.console().writer().print(ex.toString());
        }
    }

    @Override
    public void onBackPressed() {
        if(backCount){
            coordinatorLayout.setVisibility(View.GONE);
            tvTitle.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            backCount=false;
        }
        else{
            finish();
        }
    }

    @Override
    public void onItemClicked(int index, String action) {

    }
}