package com.example.nlg_project2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {

    private EditText enterUsername;
    private EditText enterPassword;
    private Button btnLogin;
    private Button btnCreateAccount;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        enterUsername = findViewById(R.id.enter_username);
        enterPassword = findViewById(R.id.enter_password);
        btnLogin = findViewById(R.id.btn_login);
        btnCreateAccount = findViewById(R.id.btn_create_account);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);


        // Set OnClickListener for create account button
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the activity where the user can create a new account
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });


        // Set OnClickListener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username and password entered by the user
                String username = enterUsername.getText().toString().trim();
                String password = enterPassword.getText().toString().trim();

                // Check if the entered username and password are valid
                if (isValidCredentials(username, password)) {
                    // Navigate to the next screen (MainActivity) if login is successful
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close the LoginActivity
                } else {
                    // Display a toast message indicating invalid login credentials
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set OnClickListener for create account button
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the activity where the user can create a new account
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to check if the entered username and password are valid
    private boolean isValidCredentials(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "users",
                null,
                "username = ? AND password = ?",
                new String[]{username, password},
                null,
                null,
                null
        );
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }


}
