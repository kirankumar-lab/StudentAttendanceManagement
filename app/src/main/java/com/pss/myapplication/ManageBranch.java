package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageBranch extends AppCompatActivity {
    private dbSAMS db = new dbSAMS(this);
    private EditText edtBranchName;
    private TextView tvAction;
    private Button btnBranch;
    private String branchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_branch);

        edtBranchName = findViewById(R.id.batchName);
        tvAction = findViewById(R.id.textAction);
        btnBranch = findViewById(R.id.btnBatch);

        String action = getIntent().getStringExtra("action");
        if (action.equals("add")) {
            tvAction.setText("Add Batch");
            btnBranch.setText("Insert");
        }
        if (action.equals("edit")) {
            tvAction.setText("Edit Batch");
            btnBranch.setText("Update");
        }
        if (action.equals("delete")) {
            tvAction.setText("Delete Batch");
            btnBranch.setText("Delete");
        }

        btnBranch.setOnClickListener(v -> {

            if (action.equals("add")) {
                if (!branchName.isEmpty()) {
                    if (db.isBatchAlready(branchName)) {
                        Toast.makeText(this, "Branch already exists !", Toast.LENGTH_SHORT).show();
                    } else {
                        String insert = db.insertBatch(branchName.toString().trim());

                        Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ManageBranch.this, Batch.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Batch Name", Toast.LENGTH_SHORT).show();
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
}