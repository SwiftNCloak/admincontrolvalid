package com.mj_bonifacio.admincontrolvalid;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {

    private SQLiteDB dbHelper;
    private String username;
    private String fullName;
    private int acceptedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        dbHelper = new SQLiteDB(this);

        // Retrieve data from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            fullName = extras.getString("fullName");
            acceptedStatus = extras.getInt("acceptedStatus");
        } else {
            // Handle the case where data is not available
            Toast.makeText(this, "Error: User details not found", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
            return;
        }

        // Display user details in the activity
        TextView usernameTextView = findViewById(R.id.detailsUsernameTextView);
        TextView fullNameTextView = findViewById(R.id.detailsFullNameTextView);
        TextView emailTextView = findViewById(R.id.detailsEmailTextView);
        TextView contactTextView = findViewById(R.id.detailsContactTextView);

        usernameTextView.setText("Username: " + username);
        fullNameTextView.setText("Full Name: " + fullName);

        // Retrieve additional user details from the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor userDetailsCursor = db.rawQuery("SELECT " +
                SQLiteDB.getEmail() + ", " +
                SQLiteDB.getContact() +
                " FROM " + SQLiteDB.getUsers() +
                " WHERE " + SQLiteDB.getColumnUser() + "=?", new String[]{username});

        String email = "";
        String contact = "";

        if (userDetailsCursor.moveToFirst()) {
            int emailIndex = userDetailsCursor.getColumnIndex(SQLiteDB.getEmail());
            int contactIndex = userDetailsCursor.getColumnIndex(SQLiteDB.getContact());

            if (emailIndex != -1) {
                email = userDetailsCursor.getString(emailIndex);
            }

            if (contactIndex != -1) {
                contact = userDetailsCursor.getString(contactIndex);
            }
        }

        emailTextView.setText("Email: " + email);
        contactTextView.setText("Contact: " + contact);

        userDetailsCursor.close();

        // Show an AlertDialog with approve/reject actions
        AlertDialog.Builder builder = new AlertDialog.Builder(UserDetailsActivity.this);
        builder.setTitle(fullName);

        // Display detailed information about the user
        View detailsView = LayoutInflater.from(UserDetailsActivity.this).inflate(R.layout.admin_details_layout, null);
        builder.setView(detailsView);

        if (acceptedStatus == 0) {
            builder.setMessage("Account is pending approval.");
        }

        builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.acceptUser(username);
                if (dbHelper.isUserAccepted(username)) {
                    Toast.makeText(UserDetailsActivity.this, "Account for " + fullName + " approved.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserDetailsActivity.this, "Failed to approve the account.", Toast.LENGTH_SHORT).show();
                }
                finish(); // Close the UserDetailsActivity
            }
        });

        builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteUser(username);
                if (!dbHelper.isUserAccepted(username)) {
                    Toast.makeText(UserDetailsActivity.this, "Account for " + fullName + " rejected.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserDetailsActivity.this, "Failed to reject the account.", Toast.LENGTH_SHORT).show();
                }
                finish(); // Close the UserDetailsActivity
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing on cancel
                finish(); // Close the UserDetailsActivity
            }
        });

        // Show the AlertDialog
        builder.show();
    }
}
