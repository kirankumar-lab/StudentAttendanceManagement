package com.pss.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    private static final String LOGGED_KEY = "com.pss.myapplication.logged";
    protected dbSAMS db;
    private Button btnChangePassword;
    private EditText oldPassword, newPassword, confirmNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        db = new dbSAMS(this);

        SharedPreferences prefs = getSharedPreferences(LOGGED_KEY, MODE_PRIVATE);
        Boolean login = prefs.getBoolean("isLoggedIn", false);

        if (login) {
            if (!prefs.getString("userType", "").equals("admin")) {
                Toast.makeText(ChangePassword.this, "Login Out Successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor =
                        getSharedPreferences(LOGGED_KEY, MODE_PRIVATE).edit();
                editor.putBoolean("isLoggedIn", false);
                editor.putString("userType", "");
                editor.apply();
                startActivity(new Intent(ChangePassword.this, MainActivity.class));
                finish();
            }
        }


        btnChangePassword.setOnClickListener(v -> {
            String oldPass = oldPassword.getText().toString().trim();
            String newPass = newPassword.getText().toString().trim();
            String confirmNewPass = confirmNewPassword.getText().toString().trim();

            if (!oldPass.isEmpty() && !newPass.isEmpty() && !confirmNewPass.isEmpty()) {
                if (newPass.equals(confirmNewPass)) {
                    if (db.check(prefs.getString("userID", ""), oldPass,
                            prefs.getString("userType", ""))) {
                        Boolean change = db.changePassword(prefs.getString("userID", ""), newPass,
                                prefs.getString("userType", ""));
                        if (change) {
                            Toast.makeText(ChangePassword.this, "Password Change Successfully",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(ChangePassword.this, "Incorrect Old Password!",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChangePassword.this, "Doesn't Match Confirm Password!",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ChangePassword.this, "Please Fill The Blanks!",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}