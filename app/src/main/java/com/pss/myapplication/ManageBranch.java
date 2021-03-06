package com.pss.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageBranch extends AppCompatActivity {

    private dbSAMS db = new dbSAMS(this);
    private TextInputEditText edtBranchName;
    private TextView tvAction;
    private Button btnBranch;
    private String branchName;
    private int bid;
    private String branch_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_branch);


        edtBranchName = findViewById(R.id.tietBranchName);
        tvAction = findViewById(R.id.textAction);
        btnBranch = findViewById(R.id.btnBranch);


        String action = getIntent().getStringExtra("action");


        if (action.equals("add")) {
            tvAction.setText("Add Branch");
            btnBranch.setText("Insert");
        }
        if (action.equals("edit")) {

            bid = getIntent().getIntExtra("bid", 0);
            branch_name = getIntent().getStringExtra("branch_name");
            edtBranchName.setText(branch_name);

            tvAction.setText("Edit Branch");
            btnBranch.setText("Update");
        }
        if (action.equals("delete")) {
            bid = getIntent().getIntExtra("bid", 0);
            branch_name = getIntent().getStringExtra("branch_name");

            tvAction.setText("Delete Branch");
            btnBranch.setText("Delete");
        }

        btnBranch.setOnClickListener(v -> {

            branchName = edtBranchName.getText().toString().trim();
            Pattern p = Pattern.compile("[a-zA-Z\\s]{5,}");
            Matcher m = p.matcher(branchName);
            boolean match = m.matches();
            if (action.equals("add")) {
                if (!branchName.isEmpty()) {
                    if (match) {
                        if (db.isBranchAlready(branchName)) {
                            Toast.makeText(this, "Branch already exists !", Toast.LENGTH_SHORT).show();
                        } else {
                            String insert = db.insertBranch(branchName.toString().trim());

                            Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageBranch.this, Branch.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Enter Branch Name minimum 5 Characters Length",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Branch Name", Toast.LENGTH_SHORT).show();
                }

            }

            if (action.equals("edit")) {
                if (!branchName.isEmpty()) {
                    if (match) {
                        if (db.isBranchAlready(branchName)) {
                            Toast.makeText(this, "Branch already exists !", Toast.LENGTH_SHORT).show();
                        } else {
                            String result = db.updateBranch(bid, branchName);
                            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ManageBranch.this, Branch.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Enter Branch Name minimum 5 Characters Length",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Branch Name", Toast.LENGTH_SHORT).show();
                }
            }

            if (action.equals("delete")) {
                    String result = db.deleteBranch(bid);
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ManageBranch.this, Branch.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
        });
    }
}