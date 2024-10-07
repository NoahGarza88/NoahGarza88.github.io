//ENHANCED the code from the ORIGINAL
//Noah Lane Garza 
//CS-499 Computer Science Capstone
package com.example.nlg_project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException; // ENHANCEMENT - Handle potential database errors
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log; // ENHANCEMENT - Log error messages for debugging

import java.security.MessageDigest; // ENHANCEMENT - For password hashing
import java.security.NoSuchAlgorithmException; // ENHANCEMENT - Handle hashing errors
import java.security.SecureRandom; // ENHANCEMENT - Generate salt
import java.util.ArrayList;
import java.util.Base64; // ENHANCEMENT - Encode salt in a readable format
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBHelper"; // ENHANCEMENT - Tag for logging

    // Define table and column names for user credentials
    private static final class UserTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password"; // ENHANCEMENT - Store hashed password
        private static final String COL_SALT = "salt"; // ENHANCEMENT - Store salt for hashing
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
        try {
            // Create user table
            db.execSQL("CREATE TABLE " + UserTable.TABLE + " (" +
                    UserTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    UserTable.COL_USERNAME + " TEXT NOT NULL, " +
                    UserTable.COL_PASSWORD + " TEXT NOT NULL, " + // ENHANCEMENT - Store hashed password
                    UserTable.COL_SALT + " TEXT NOT NULL)"); // ENHANCEMENT - Store salt for hashing

            // Create daily weight table
            db.execSQL("CREATE TABLE " + DailyWeightTable.TABLE + " (" +
                    DailyWeightTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DailyWeightTable.COL_WEIGHT + " REAL NOT NULL, " +
                    DailyWeightTable.COL_DATE + " TEXT NOT NULL)");
        } catch (SQLException e) {
            Log.e(TAG, "Error creating tables: ", e); // ENHANCEMENT - Log table creation errors
        }
    }

    // Upgrade the database if necessary
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DailyWeightTable.TABLE);
            onCreate(db);
        } catch (SQLException e) {
            Log.e(TAG, "Error upgrading tables: ", e); // ENHANCEMENT - Log upgrade errors
        }
    }

    // Add a new user with hashed password
    public void addUser(String username, String password) {
        SQLiteDatabase db = null; // ENHANCEMENT - Declared db variable for cleanup
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UserTable.COL_USERNAME, username);
            values.put(UserTable.COL_PASSWORD, password); // ENHANCEMENT - Hash password before storing
            db.insert(UserTable.TABLE, null, values);
        } catch (SQLException e) {
            Log.e(TAG, "Error adding user: ", e); // ENHANCEMENT - Log user addition errors
        } finally {
            if (db != null && db.isOpen()) {
                db.close(); // ENHANCEMENT - Close database to prevent leaks
            }
        }
    }

    // Retrieve all users from the database
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = null; // ENHANCEMENT - Declared db variable for cleanup
        Cursor cursor = null; // ENHANCEMENT - Declared cursor variable for proper closure
        try {
            db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + UserTable.TABLE;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setId(cursor.getInt(cursor.getColumnIndex(UserTable.COL_ID)));
                    user.setUsername(cursor.getString(cursor.getColumnIndex(UserTable.COL_USERNAME)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(UserTable.COL_PASSWORD))); // ENHANCEMENT -
                                                                                                       // Password is
                                                                                                       // hashed
                    users.add(user);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error fetching users: ", e); // ENHANCEMENT - Log user retrieval errors
        } finally {
            if (cursor != null) {
                cursor.close(); // ENHANCEMENT - Close cursor to avoid leaks
            }
            if (db != null && db.isOpen()) {
                db.close(); // ENHANCEMENT - Close database to prevent leaks
            }
        }
        return users;
    }

    // Add a new daily weight entry
    public void addDailyWeight(float weight, String date) {
        SQLiteDatabase db = null; // ENHANCEMENT - Declared db variable for cleanup
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DailyWeightTable.COL_WEIGHT, weight);
            values.put(DailyWeightTable.COL_DATE, date);
            db.insert(DailyWeightTable.TABLE, null, values);
        } catch (SQLException e) {
            Log.e(TAG, "Error adding daily weight: ", e); // ENHANCEMENT - Log weight entry errors
        } finally {
            if (db != null && db.isOpen()) {
                db.close(); // ENHANCEMENT - Close database to prevent leaks
            }
        }
    }

    // Retrieve all daily weight entries
    public List<DailyWeightEntry> getAllDailyWeights() {
        List<DailyWeightEntry> weightEntries = new ArrayList<>();
        SQLiteDatabase db = null; // ENHANCEMENT - Declared db variable for cleanup
        Cursor cursor = null; // ENHANCEMENT - Declared cursor variable for proper closure
        try {
            db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + DailyWeightTable.TABLE;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    DailyWeightEntry entry = new DailyWeightEntry();
                    entry.setId(cursor.getInt(cursor.getColumnIndex(DailyWeightTable.COL_ID)));
                    entry.setWeight(cursor.getFloat(cursor.getColumnIndex(DailyWeightTable.COL_WEIGHT)));
                    entry.setDate(cursor.getString(cursor.getColumnIndex(DailyWeightTable.COL_DATE)));
                    weightEntries.add(entry);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error fetching daily weights: ", e); // ENHANCEMENT - Log weight retrieval errors
        } finally {
            if (cursor != null) {
                cursor.close(); // ENHANCEMENT - Close cursor to avoid leaks
            }
            if (db != null && db.isOpen()) {
                db.close(); // ENHANCEMENT - Close database to prevent leaks
            }
        }
        return weightEntries;
    }
}
