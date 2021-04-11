package com.pss.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    private static final String LOGGED_KEY = "com.pss.myapplication.logged";

    //create variables
    private TextView tvUser,tvForgetPassword;
    private TextInputEditText edtUserID, edtPassword;
    private Button btnLogin;
    protected String user;
    protected dbSAMS db;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new dbSAMS(this);

        user = getIntent().getStringExtra("user");

        //set variables
        tvUser = findViewById(R.id.tvUser);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        edtUserID = findViewById(R.id.user_id);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        //admin login
        if (user.equals("admin")) {
            tvUser.setText(R.string.admin_login);
            tvForgetPassword.setVisibility(View.GONE);
            Drawable drawable = tvUser.getContext().getResources().getDrawable(R.drawable.ic_admin);
            tvUser.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
        }

        //staff login
        if (user.equals("staff")) {
            tvUser.setText(R.string.staff_login);
            tvForgetPassword.setVisibility(View.VISIBLE);
            Drawable drawable = tvUser.getContext().getResources().getDrawable(R.drawable.ic_staff);
            tvUser.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
        }

        //student login
        if (user.equals("student")) {
            tvUser.setText(R.string.student_login);
            tvForgetPassword.setVisibility(View.VISIBLE);
            Drawable drawable =
                    tvUser.getContext().getResources().getDrawable(R.drawable.ic_student);
            tvUser.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
        }

        btnLogin.setOnClickListener(v -> {
            String user_id = edtUserID.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            if (user_id.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please Fill Blanks!", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Boolean check = db.check(user_id, password, user);
                    if (!check) {
                        Toast.makeText(Login.this, "Please Enter Correct User ID and Password!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        logged(user_id, user);
                        if (user.equals("admin")) {
                            startActivity(new Intent(Login.this, AdminPanel.class));
                        }
                        if (user.equals("staff")) {
                            startActivity(new Intent(Login.this, StaffPanel.class));
                        }
                        if (user.equals("student")) {
                            startActivity(new Intent(Login.this, StudentPanel.class));
                        }
                        finish();
                    }
                } catch (SQLException e) {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Login.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void logged(String userID, String userType) {
        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor =
                getSharedPreferences(LOGGED_KEY, MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("userType", userType);
        editor.putString("userID", userID);
        editor.apply();
    }

    /////////////////////////////////////////////////////////////////////////////////
    public void forgotPassword(View v)
    {
        Intent i = new Intent(Login.this,ForgotPassword.class);
        i.putExtra("user",user);
        startActivity(i);
    }
}