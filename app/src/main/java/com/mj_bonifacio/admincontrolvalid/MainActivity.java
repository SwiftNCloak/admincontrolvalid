package com.mj_bonifacio.admincontrolvalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Initialize elements
    Button loginBtn, signupBtn;
    EditText usernameTxt, passTxt;
    Intent loginInit, signupInit, adminInit;
    SQLiteDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLiteDB(this);

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
                } else {
                    // Check if the user exists in the database and is accepted
                    if (userExists(username, password) && dbHelper.isUserAccepted(username)) {
                        Toast.makeText(MainActivity.this, "GUEST LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        loginInit = new Intent(MainActivity.this, GuestProfile.class);
                        loginInit.putExtra("USERNAME", username); // Pass the username to GuestProfile
                        startActivity(loginInit);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid username or password, or not yet accepted by admin.", Toast.LENGTH_SHORT).show();
                    }
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

    // Validate if data exists
    private boolean userExists(String username, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {SQLiteDB.getColumnUser(), SQLiteDB.getColumnPass()};
        String selection = SQLiteDB.getColumnUser() + "=? AND " + SQLiteDB.getColumnPass() + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(SQLiteDB.getUsers(), columns, selection, selectionArgs, null, null, null);

        boolean isValid = cursor.moveToFirst();

        cursor.close();
        db.close();

        return isValid;
    }
}