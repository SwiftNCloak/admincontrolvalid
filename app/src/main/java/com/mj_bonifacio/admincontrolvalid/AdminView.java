package com.mj_bonifacio.admincontrolvalid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminView extends AppCompatActivity {

    private SQLiteDB dbHelper;
    private ListView adminListView; // Declare as a class variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        dbHelper = new SQLiteDB(this);

        // Retrieve data from the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " +
                SQLiteDB.COLUMN_ID + " AS _id, " +
                SQLiteDB.COLUMN_FIRST_NAME + " || ' ' || " +
                SQLiteDB.COLUMN_MIDDLE_NAME + " || ' ' || " +
                SQLiteDB.COLUMN_LAST_NAME + " AS " + SQLiteDB.COLUMN_FIRST_NAME +
                ", " + SQLiteDB.COLUMN_ACCEPTED +
                " FROM " + SQLiteDB.TABLE_USERS, null);

        // Map cursor columns to UI elements
        String[] fromColumns = {
                SQLiteDB.COLUMN_FIRST_NAME,
                SQLiteDB.COLUMN_ACCEPTED
        };

        int[] toViews = {
                R.id.adminNameTextView,
                R.id.acceptedTextView
        };

        // Create and set the adapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.admin_list_item_layout,
                cursor,
                fromColumns,
                toViews,
                0
        );

        // Set the adapter for the ListView in AdminView
        adminListView = findViewById(R.id.adminListView);
        adminListView.setAdapter(adapter);

        adminListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve data for the selected item
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                if (cursor != null) {
                    int usernameColumnIndex = cursor.getColumnIndex(SQLiteDB.COLUMN_USERNAME);
                    int fullNameColumnIndex = cursor.getColumnIndex(SQLiteDB.COLUMN_FIRST_NAME);
                    int acceptedColumnIndex = cursor.getColumnIndex(SQLiteDB.COLUMN_ACCEPTED);

                    if (usernameColumnIndex != -1 && fullNameColumnIndex != -1 && acceptedColumnIndex != -1) {
                        final String username = cursor.getString(usernameColumnIndex);
                        final String fullName = cursor.getString(fullNameColumnIndex);
                        final int acceptedStatus = cursor.getInt(acceptedColumnIndex);

                        // Create an Intent to start UserDetailsActivity
                        Intent intent = new Intent(AdminView.this, UserDetailsActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AdminView.this, "Column index not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
