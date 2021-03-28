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

public class ManageBranch extends AppCompatActivity {

    private dbSAMS db = new dbSAMS(this);
    private EditText edtBranchName;
    private TextView tvAction;
    private Button btnBranch;
    private String branchName;
    private int bid;
    private String branch_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_branch);

        edtBranchName = findViewById(R.id.batchName);
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
            tvAction.setText("Delete Branch");
            btnBranch.setText("Delete");
        }

        btnBranch.setOnClickListener(v -> {
            try {
                branchName = edtBranchName.getText().toString().trim();
                Pattern p = Pattern.compile("[a-zA-z]");
                Matcher m = p.matcher(branchName);
                boolean match = m.matches();
            }
            catch(Exception ex){
                Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}