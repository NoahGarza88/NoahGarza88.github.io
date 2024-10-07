package com.example.nlg_project2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {

    private Switch switchPermission;
    private Switch switchCalendar;
    private Switch switchContacts;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        // Find the "Back" button
        Button btnBack = findViewById(R.id.btnBack);

        // Set a click listener for the "Back" button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the SettingsActivity and go back to MainActivity
                finish();
            }
        });

        // Initialize switches
        switchPermission = findViewById(R.id.switch1);
        switchCalendar = findViewById(R.id.switch2);
        switchContacts = findViewById(R.id.switch3);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);

        // Retrieve and set switch states
        switchPermission.setChecked(sharedPreferences.getBoolean("Permission", false));
        switchCalendar.setChecked(sharedPreferences.getBoolean("Calendar", false));
        switchContacts.setChecked(sharedPreferences.getBoolean("Contacts", false));

        // Handle switch changes
        switchPermission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update permission status
                sharedPreferences.edit().putBoolean("Permission", isChecked).apply();
            }
        });

        switchCalendar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update calendar permission status
                sharedPreferences.edit().putBoolean("Calendar", isChecked).apply();
            }
        });

        switchContacts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update contacts permission status
                sharedPreferences.edit().putBoolean("Contacts", isChecked).apply();
            }
        });
    }

    public void goBack(View view) {
        finish(); // Close the SettingsActivity and go back to MainActivity
    }
}
