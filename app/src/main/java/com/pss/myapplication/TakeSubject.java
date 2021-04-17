package com.pss.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TakeSubject extends AppCompatActivity implements AdapterTakeSubject.ItemClicked{
    private static final String LOGGED_KEY = "com.pss.myapplication.logged";
    private RecyclerView dataTakeSubject;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListTakeSubject> data;
    private dbSAMS db;
    private Dialog dialog;
    private Button btnCancle,btnYes;
    private TextView tvDeleteSubjectName,takeSubjectView,tackSubjectProfessor;
    private boolean isAdmin = false;
    private CardView cardView;
    private FloatingActionButton fabAdd;
    private AutoCompleteTextView actvBatch,actvBranch;
    private ArrayList<String> fetchedBatch = new ArrayList<>();
    private ArrayList<String> fetchedBranch = new ArrayList<>();
    private ArrayAdapter<String> fetchedBatchAdap,fetchedBranchAdap;
    private String selectedBatch="",selectedBranch="";
    private String prof_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_subject);

        cardView = (CardView) findViewById(R.id.search_take_subject);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);

        SharedPreferences prefs = getSharedPreferences(LOGGED_KEY, MODE_PRIVATE);

        prof_id = prefs.getString("userID", "");

        db = new dbSAMS(this);

        dataTakeSubject = findViewById(R.id.dataTakeSubject);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        dataTakeSubject.setLayoutManager(manager);

        data = new ArrayList<>();

        Cursor r = db.getTakeSubject(prof_id);

        if (prefs.getString("userType", "").equals("admin")) {
            try {
                isAdmin =true;
                fabAdd.setVisibility(View.GONE);
                r = db.getTakeSubjectAll();
            }
            catch(Exception ex){
                Toast.makeText(this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            cardView.setVisibility(View.GONE);
        }

        while(r.moveToNext())
        {
            data.add(new ListTakeSubject(Integer.parseInt(r.getString(0)),
                    String.valueOf(db.getTakeSubjectName(r.getString(4))),
                            Integer.parseInt(db.getTakeSubjectSemester(r.getString(4))),
                    Integer.parseInt(r.getString(1)),
                                    db.getBatchName(Integer.parseInt((r.getString(2)))),
                                            db.getProfessorName(Integer.parseInt(r.getString(3))),
                                                    Integer.parseInt(r.getString(5))));
        }

        myAdapter = new AdapterTakeSubject(this,data,isAdmin);
        dataTakeSubject.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addSubject(View v) {
        Intent i  = new Intent(TakeSubject.this, ManageTakeSubject.class);
        i.putExtra("prof_id",prof_id);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myAdapter.notifyDataSetChanged();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 1)
//        {
//            if(requestCode == RESULT_OK)
//            {
//                myAdapter.notifyDataSetChanged();
//            }
//        }
//    }

//    private boolean isYes;

    @Override
    public void onItemClicked(int index,String action) {
        if (action.equals("delete")){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.layout_delete_take_subject);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            btnCancle = dialog.findViewById(R.id.btnCancle);
            btnYes = dialog.findViewById(R.id.btnYes);
            tvDeleteSubjectName = dialog.findViewById(R.id.tvDeleteTakeSubjectName);
            tvDeleteSubjectName.setText("Are You Sure to Delete \""+data.get(index).getSubject_name()+"\" Subject?");

            dialog.show();


            btnCancle.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnYes.setOnClickListener(v -> {
                String delete = db.deleteTakeSubject(String.valueOf(data.get(index).getTsid()));
                Toast.makeText(this,delete,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                data.remove(index);
                myAdapter.notifyDataSetChanged();
            });
        }
    }
}