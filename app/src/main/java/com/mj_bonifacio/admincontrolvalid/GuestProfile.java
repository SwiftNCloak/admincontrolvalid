package com.mj_bonifacio.admincontrolvalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class GuestProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USERNAME")) {
            String username = intent.getStringExtra("USERNAME");
            // Call a method to fetch user data using the username
            displayUserData(username);
        }
    }

    private void displayUserData(String username) {
        SQLiteDB dbHelper = new SQLiteDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                SQLiteDB.getFirstN(),
                SQLiteDB.getMiddleN(),
                SQLiteDB.getLastN(),
                SQLiteDB.getEmail(),
                SQLiteDB.getContact()
        };

        String selection = SQLiteDB.getColumnUser() + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(SQLiteDB.getUsers(), columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int firstNameIndex = cursor.getColumnIndex(SQLiteDB.getFirstN());
            int middleNameIndex = cursor.getColumnIndex(SQLiteDB.getMiddleN());
            int lastNameIndex = cursor.getColumnIndex(SQLiteDB.getLastN());
            int emailIndex = cursor.getColumnIndex(SQLiteDB.getEmail());
            int contactIndex = cursor.getColumnIndex(SQLiteDB.getContact());

            String firstName = cursor.getString(firstNameIndex);
            String middleName = cursor.getString(middleNameIndex);
            String lastName = cursor.getString(lastNameIndex);
            String email = cursor.getString(emailIndex);
            String contact = cursor.getString(contactIndex);

            TextView nameTextView = findViewById(R.id.nameTextView);
            TextView emailTextView = findViewById(R.id.emailTextView);
            TextView contactTextView = findViewById(R.id.contactTextView);

            nameTextView.setText("Name: " + firstName + " " + middleName + " " + lastName);
            emailTextView.setText("Email: " + email);
            contactTextView.setText("Contact: " + contact);
        }

        cursor.close();
        db.close();
    }
}