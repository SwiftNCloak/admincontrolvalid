package com.mj_bonifacio.admincontrolvalid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "accounts";
    private static final int DATABASE_VERSION = 2; // Increment the version number

    // Information
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_MIDDLE_NAME = "middle_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ACCEPTED = "accepted"; // Add a column to track acceptance status

    // SQL statements
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FIRST_NAME + " TEXT, "
            + COLUMN_MIDDLE_NAME + " TEXT, "
            + COLUMN_LAST_NAME + " TEXT, "
            + COLUMN_EMAIL + " TEXT, "
            + COLUMN_CONTACT + " TEXT, "
            + COLUMN_USERNAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_ACCEPTED + " INTEGER DEFAULT 0);"; // Default to not accepted

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle upgrades if needed
        if (oldVersion < 2) {
            // Add the new column in case of an upgrade
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ACCEPTED + " INTEGER DEFAULT 0");
        }
    }

    public static String getUsers() {
        return TABLE_USERS;
    }

    public static String getFirstN() {
        return COLUMN_FIRST_NAME;
    }

    public static String getMiddleN() {
        return COLUMN_MIDDLE_NAME;
    }

    public static String getLastN() {
        return COLUMN_LAST_NAME;
    }

    public static String getEmail() {
        return COLUMN_EMAIL;
    }

    public static String getContact() {
        return COLUMN_CONTACT;
    }

    public static String getColumnUser() {
        return COLUMN_USERNAME;
    }

    public static String getColumnPass() {
        return COLUMN_PASSWORD;
    }

    public static String getColumnAccepted() {
        return COLUMN_ACCEPTED;
    }

    public long addUser(String firstN, String middleN, String lastN,
                        String email, String contact, String user, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, firstN);
        values.put(COLUMN_MIDDLE_NAME, middleN);
        values.put(COLUMN_LAST_NAME, lastN);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_CONTACT, contact);
        values.put(COLUMN_USERNAME, user);
        values.put(COLUMN_PASSWORD, pass);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }

    public void acceptUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCEPTED, 1); // 1 indicates accepted
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "=?", new String[]{username});
        db.close();
    }

    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_USERNAME + "=?", new String[]{username});
        db.close();
    }

    public boolean isUserAccepted(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Check if the user is accepted by querying the database
        String[] columns = {COLUMN_ACCEPTED};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        boolean isAccepted = false;
        if (cursor.moveToFirst()) {
            int acceptedIndex = cursor.getColumnIndex(COLUMN_ACCEPTED);
            isAccepted = cursor.getInt(acceptedIndex) == 1;
        }

        cursor.close();
        db.close();
        return isAccepted;
    }
}
