package com.pss.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Professor extends AppCompatActivity implements AdapterProfessor.ItemClicked {

    private RecyclerView dataProfessor;
    RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<ListProfessor> data;
    private dbSAMS db;
    private Dialog dialog;
    private Button btnCancle, btnYes;
    private TextView tvDeleteProfessorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        db = new dbSAMS(this);

        dataProfessor = findViewById(R.id.dataProfesor);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dataProfessor.setLayoutManager(manager);

        data = new ArrayList<>();

        Cursor r = db.getAllProfessor();

        while (r.moveToNext()) {
            data.add(new ListProfessor(Integer.parseInt(r.getString(0)), r.getString(1),
                    r.getString(2), r.getString(3),
                    r.getString(4),
                    Integer.parseInt(r.getString(5))));
        }

        myAdapter = new AdapterProfessor(this, data);
        dataProfessor.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    public void addProfessor(View view) {
        Intent i = new Intent(Professor.this, ManageProfessor.class).putExtra("action", "add");
        startActivityForResult(i, 1);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (requestCode == RESULT_OK) {
                myAdapter.notifyDataSetChanged();
            }
        }
    }

//    private boolean isYes;

    @Override
    public void onItemClicked(int index, String action) {
        if (action.equals("edit")) {
            startActivity(new Intent(Professor.this, ManageProfessor.class)
                    .putExtra("sid", data.get(index).getSid())
                    .putExtra("professor_name", data.get(index).getProfessor_name())
                    .putExtra("professor_mobileno",
                            data.get(index).getProfessor_mobileno())
                    .putExtra("professor_email", data.get(index).getProfessor_email())
                    .putExtra("professor_password", data.get(index).getProfessor_password())
                    .putExtra("bid", data.get(index).getBid())
                    .putExtra("action", action));
        }
        if (action.equals("delete")) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.layout_delete_professor);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            btnCancle = dialog.findViewById(R.id.btnCancle);
            btnYes = dialog.findViewById(R.id.btnYes);
            tvDeleteProfessorName = dialog.findViewById(R.id.tvDeleteProfessorName);
            tvDeleteProfessorName.setText("Are You Sure to Delete \"" + data.get(index).getProfessor_name() + "\" Professor Details?");

            dialog.show();


            btnCancle.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnYes.setOnClickListener(v -> {
                String delete = db.deleteProfessor(data.get(index).getSid());
                Toast.makeText(this, delete, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                data.remove(index);
                myAdapter.notifyDataSetChanged();
            });
        }
    }


}