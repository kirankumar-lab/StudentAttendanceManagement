package com.pss.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageBatch extends AppCompatActivity {

    private dbSAMS db = new dbSAMS(this);
    private EditText edtBatchName;
    private TextView tvAction;
    private Button btnBatch;
    private String batchName;
    private int btid;
    private String batch_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_batch);


        edtBatchName = findViewById(R.id.batchName);
        tvAction = findViewById(R.id.textAction);
        btnBatch = findViewById(R.id.btnBatch);


        String action = getIntent().getStringExtra("action");



        if (action.equals("add")) {
            tvAction.setText("Add Batch");
            btnBatch.setText("Insert");
        }
        if (action.equals("edit")) {

            btid = getIntent().getIntExtra("btid",0);
            batch_name = getIntent().getStringExtra("batch_name");
            edtBatchName.setText(batch_name);

            tvAction.setText("Edit Batch");
            btnBatch.setText("Update");
        }
        if (action.equals("delete")) {
            tvAction.setText("Delete Batch");
            btnBatch.setText("Delete");
        }

        btnBatch.setOnClickListener(v -> {

            batchName = edtBatchName.getText().toString().trim();
            Pattern p = Pattern.compile("[2][0][0-9]{2}[-][2][0][0-9]{2}");
            Matcher m = p.matcher(batchName);
            boolean match = m.matches();

            if (action.equals("add")) {
                if (!batchName.isEmpty() && match) {
                    if (db.isBatchAlready(batchName)) {
                        Toast.makeText(this, "Batch already exists !", Toast.LENGTH_SHORT).show();
                    } else {
                        String insert = db.insertBatch(batchName.toString().trim());

                        Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ManageBatch.this, Batch.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Batch Name", Toast.LENGTH_SHORT).show();
                }

            }

            if (action.equals("edit")) {
                if (!batchName.isEmpty() && match) {
                    if (db.isBatchAlready(batchName)) {
                        Toast.makeText(this, "Batch already exists !", Toast.LENGTH_SHORT).show();
                    } else {
                        String result = db.updateBatch(btid,batchName);

                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ManageBatch.this, Batch.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Batch Name", Toast.LENGTH_SHORT).show();
                }
            }
//
//            if (action.equals("delete")) {
//                if (!batchName.isEmpty()) {
//                    String delete = new dbSAMS(this).deleteBatch(batchName);
//                    Toast.makeText(this, delete, Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
        });
    }
}