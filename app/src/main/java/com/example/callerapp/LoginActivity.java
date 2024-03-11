package com.example.callerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.callerapp.DAO.UserDAO;
import com.example.callerapp.DAOImplementation.UserDAOImplementation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText, passwordEditText;
    private TextInputLayout usernameInputLayout, passwordInputLayout;
    private Button loginButton , createAccountButton;

    private CheckBox rememberMeCheckbox;
    private SharedPreferences sharedPreferences;

    private UserDAO userDAO;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameInputLayout = findViewById(R.id.usernameInput);
        passwordInputLayout = findViewById(R.id.passwordInput);
        usernameEditText = (TextInputEditText) usernameInputLayout.getEditText();
        passwordEditText = (TextInputEditText) passwordInputLayout.getEditText();
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        // Initialize DatabaseHelper
        ProfileDBHelper dbHelper = new ProfileDBHelper(getApplicationContext());



        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

//                // Save credentials if "Remember Me" is checked
                if (rememberMeCheckbox.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rememberMe", true);
                    editor.apply();
                } else {
                    // Clear saved credentials if "Remember Me" is unchecked
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();
                }

                // Validate input
                if (username.isEmpty()){
                    usernameInputLayout.setError("Email is required");
                }
                if (password.isEmpty()){
                    passwordInputLayout.setError("Password is required");
                }

                   userDAO = new UserDAOImplementation(LoginActivity.this);
                   user = userDAO.login(username, password);


                    // Check if the user exists in the database
                    if (user != null){
                        // Redirect to main activity or another activity
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Finish login activity
                    } else {
                        // Show error if login fails
                        usernameInputLayout.setError("Invalid username or password");
                        passwordInputLayout.setError("Invalid username or password");
                    }

            }
        });

        // Set click listener for create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to register activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Check if "Remember Me" is checked and set the checkbox accordingly
        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        rememberMeCheckbox.setChecked(rememberMe);

        // Set click listener for remember me checkbox
        rememberMeCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store the state of the checkbox in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rememberMe", rememberMeCheckbox.isChecked());
                editor.apply();
            }
        });
    }
}
