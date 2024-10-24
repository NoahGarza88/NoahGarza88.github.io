package com.example.nlg_project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    // Define table and column names for user credentials
    private static final class UserTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }

    // Define table and column names for daily weight data
    private static final class DailyWeightTable {
        private static final String TABLE = "daily_weight";
        private static final String COL_ID = "_id";
        private static final String COL_WEIGHT = "weight";
        private static final String COL_DATE = "date";
    }

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table
        db.execSQL("CREATE TABLE " + UserTable.TABLE + " (" +
                UserTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COL_USERNAME + " TEXT NOT NULL, " +
                UserTable.COL_PASSWORD + " TEXT NOT NULL)");

        // Create daily weight table
        db.execSQL("CREATE TABLE " + DailyWeightTable.TABLE + " (" +
                DailyWeightTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DailyWeightTable.COL_WEIGHT + " REAL NOT NULL, " +
                DailyWeightTable.COL_DATE + " TEXT NOT NULL)");
    }

    // Upgrade the database (drop tables and recreate) if necessary
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DailyWeightTable.TABLE);
        onCreate(db);
    }

    // Method to add a new user to the database
    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTable.COL_USERNAME, username);
        values.put(UserTable.COL_PASSWORD, password);
        db.insert(UserTable.TABLE, null, values);
        db.close();
    }

    // Method to retrieve all users from the database
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + UserTable.TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(UserTable.COL_ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(UserTable.COL_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(UserTable.COL_PASSWORD)));
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    // Method to add a new daily weight entry to the database
    public void addDailyWeight(float weight, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DailyWeightTable.COL_WEIGHT, weight);
        values.put(DailyWeightTable.COL_DATE, date);
        db.insert(DailyWeightTable.TABLE, null, values);
        db.close();
    }

    // Method to retrieve all daily weight entries from the database
    public List<DailyWeightEntry> getAllDailyWeights() {
        List<DailyWeightEntry> weightEntries = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DailyWeightTable.TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DailyWeightEntry entry = new DailyWeightEntry();
                entry.setId(cursor.getInt(cursor.getColumnIndex(DailyWeightTable.COL_ID)));
                entry.setWeight(cursor.getFloat(cursor.getColumnIndex(DailyWeightTable.COL_WEIGHT)));
                entry.setDate(cursor.getString(cursor.getColumnIndex(DailyWeightTable.COL_DATE)));
                weightEntries.add(entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return weightEntries;
    }
}
