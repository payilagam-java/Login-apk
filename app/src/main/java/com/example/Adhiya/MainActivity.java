package com.example.Adhiya;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.splash.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    // Dummy user credentials map (replace with your actual user authentication logic)
    private Map<String, String> userCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        // Initialize user credentials (for demo purposes)
        userCredentials = new HashMap<>();
        userCredentials.put("user", "1234");
        userCredentials.put("Arivu@123", "Ad112");
        // Add more users as needed

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get entered username and password
                String enteredUsername = usernameEditText.getText().toString().trim();
                String enteredPassword = passwordEditText.getText().toString().trim();

                // Validate username and password
                if (TextUtils.isEmpty(enteredUsername)) {
                    usernameEditText.setError("Please enter your username");
                    return;
                }

                if (TextUtils.isEmpty(enteredPassword)) {
                    passwordEditText.setError("Please enter your password");
                    return;
                }

                // Check if entered credentials are valid
                if (userCredentials.containsKey(enteredUsername)) {
                    if (userCredentials.get(enteredUsername).equals(enteredPassword)) {
                        // Login successful
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Navigate to BorrowerActivity
                        Intent borrowerIntent = new Intent(MainActivity.this, BorrowerActivity.class);
                        borrowerIntent.putExtra("username", enteredUsername);
                        startActivity(borrowerIntent);

                        // Finish MainActivity
                        finish();
                    } else {
                        // Incorrect password
                        Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // User not found
                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
