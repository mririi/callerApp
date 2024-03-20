package com.example.callerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.callerapp.DAO.UserDAO;
import com.example.callerapp.DAOImplementation.UserDAOImplementation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText fullnameEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    private TextInputLayout fullnameInputLayout, usernameInputLayout, passwordInputLayout, confirmPasswordInputLayout;
    private Button registerButton, cancelButton;

    private UserDAO userDAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameInputLayout = findViewById(R.id.fullnameInput);
        usernameInputLayout = findViewById(R.id.usernameInput);
        passwordInputLayout = findViewById(R.id.passwordInput);
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInput);
        fullnameEditText = (TextInputEditText) fullnameInputLayout.getEditText();
        usernameEditText = (TextInputEditText) usernameInputLayout.getEditText();
        passwordEditText = (TextInputEditText) passwordInputLayout.getEditText();
        confirmPasswordEditText = (TextInputEditText) confirmPasswordInputLayout.getEditText();
        registerButton = findViewById(R.id.registerButton);
        cancelButton = findViewById(R.id.cancelButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String fullname = fullnameEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();


                if (fullname.isEmpty()){
                    fullnameInputLayout.setError("Fullname is required");
                    return;
                }
                if (username.isEmpty()){
                    usernameInputLayout.setError("Username is required");
                    return;
                }
                if (password.isEmpty()){
                    passwordInputLayout.setError("Password is required");
                    return;
                }
                if (confirmPassword.isEmpty()){
                    confirmPasswordInputLayout.setError("Confirm password is required");
                    return;
                }
                 if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                     return;
                }
                userDAO = new UserDAOImplementation(RegisterActivity.this);
                User user = new User(fullname, username, password);

                if (userDAO.register(user)) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}