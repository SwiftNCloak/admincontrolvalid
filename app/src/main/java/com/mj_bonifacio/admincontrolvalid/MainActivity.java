package com.mj_bonifacio.admincontrolvalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Initialize elements
    Button loginBtn, signupBtn;
    EditText usernameTxt, passTxt;
    Intent loginInit, signupInit, adminInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign based on IDs in the xml
        loginBtn = (Button) findViewById(R.id.btnLogin);
        signupBtn = (Button) findViewById(R.id.btnSignUp);
        usernameTxt = (EditText) findViewById(R.id.txtUsername);
        passTxt = (EditText) findViewById(R.id.txtPassword);
        passTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Log-In Button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameTxt.getText().toString().trim();
                String password = passTxt.getText().toString().trim();

                // Main admin credentials
                String adminUsername = "admin";
                String adminPass = "1234";

                if (username.equals(adminUsername) && password.equals(adminPass)){
                    Toast.makeText(MainActivity.this, "ADMIN LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    adminInit = new Intent(MainActivity.this, AdminView.class);
                    startActivity(adminInit);
                }

                else if (!username.isEmpty() && !password.isEmpty()){
                    Toast.makeText(MainActivity.this, "GUEST LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    loginInit = new Intent(MainActivity.this, GuestProfile.class);
                    startActivity(loginInit);
                }

                else {
                    Toast.makeText(MainActivity.this, "Please enter username and password first before logging in.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sign Up Button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "SIGN UP", Toast.LENGTH_SHORT).show();
                signupInit = new Intent(MainActivity.this, SignUpView.class);
                startActivity(signupInit);
            }
        });
    }
}