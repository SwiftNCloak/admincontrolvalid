package com.mj_bonifacio.admincontrolvalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpView extends AppCompatActivity {
    // Initialize elements
    Button regBtn, backBtn;
    SQLiteDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_view);

        dbHelper = new SQLiteDB(this);

        // Stuffs
        regBtn = (Button) findViewById(R.id.btnRegister);
        backBtn = (Button) findViewById(R.id.btnBack);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(SignUpView.this, MainActivity.class);
                startActivity(goBack);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = ((EditText) findViewById(R.id.txtFirstName)).getText().toString().trim();
                String middleName = ((EditText) findViewById(R.id.txtMiddleName)).getText().toString().trim();
                String lastName = ((EditText) findViewById(R.id.txtLastName)).getText().toString().trim();
                String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString().trim();
                String contact = ((EditText) findViewById(R.id.txtContact)).getText().toString().trim();
                String username = ((EditText) findViewById(R.id.txtUsername)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString().trim();

                // Check if any of the fields are empty
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contact.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpView.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // Add the user to the database
                    long result = dbHelper.addUser(firstName, middleName, lastName, email, contact, username, password);

                    // Check if user was successfully added
                    if (result != -1) {
                        Toast.makeText(SignUpView.this, "Account added successfully.", Toast.LENGTH_SHORT).show();
                        Intent goBack = new Intent(SignUpView.this, MainActivity.class);
                        startActivity(goBack);
                    } else {
                        Toast.makeText(SignUpView.this, "Failed to add account. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}