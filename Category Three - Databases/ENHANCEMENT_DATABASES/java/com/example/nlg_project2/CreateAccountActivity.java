package com.example.nlg_project2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText enterNewUsername;
    private EditText enterNewPassword;
    private Button btnCreateAccount;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize UI elements
        enterNewUsername = findViewById(R.id.enter_new_username);
        enterNewPassword = findViewById(R.id.enter_new_password);
        btnCreateAccount = findViewById(R.id.btn_create_account);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Set OnClickListener for create account button
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new username and password entered by the user
                String newUsername = enterNewUsername.getText().toString().trim();
                String newPassword = enterNewPassword.getText().toString().trim();

                // Check if the new username is not empty
                if (!newUsername.isEmpty()) {
                    // Insert the new username and password into the database
                    insertNewUser(newUsername, newPassword);
                } else {
                    // Display a toast message indicating that the username cannot be empty
                    Toast.makeText(CreateAccountActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to insert a new user into the database
    private void insertNewUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long newRowId = db.insert("users", null, values);
        if (newRowId != -1) {
            // Display a toast message indicating successful account creation
            Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the CreateAccountActivity
        } else {
            // Display a toast message indicating failed account creation
            Toast.makeText(CreateAccountActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
        }
    }
}
