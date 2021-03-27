package com.pss.myapplication;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManageBatch extends AppCompatActivity {

    private EditText edtBatchName;
    private TextView tvAction;
    private Button btnBatch;
    private String batchName;
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
            tvAction.setText("Edit Batch");
            btnBatch.setText("Update");
        }
        if (action.equals("delete")) {
            tvAction.setText("Delete Batch");
            btnBatch.setText("Delete");
        }

        btnBatch.setOnClickListener(v -> {
            batchName = edtBatchName.getText().toString().trim();
            if (action.equals("add")) {
                if (!batchName.isEmpty()) {
                    String insert = new dbSAMS(this).insertBatch(batchName.toString().trim());
                    Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Please Enter Batch Name", Toast.LENGTH_SHORT).show();
                }
            }

//            if (action.equals("edit")) {
//                if (!batchName.isEmpty()) {
//                    String update = new dbSAMS(this).updateBatch(batchName);
//                    Toast.makeText(this, update, Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(this, "Please Enter Batch Name", Toast.LENGTH_SHORT).show();
//                }
//            }
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