package com.pss.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Branch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addBranch(View view)
    {
            Intent i = new Intent(Branch.this, ManageBranch.class).putExtra("action","add");
            startActivityForResult(i,1);
    }
}