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

        usernameInputLayout = findViewById(R.id.usernameInput);
        passwordInputLayout = findViewById(R.id.passwordInput);
        usernameEditText = (TextInputEditText) usernameInputLayout.getEditText();
        passwordEditText = (TextInputEditText) passwordInputLayout.getEditText();
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        ProfileDBHelper dbHelper = new ProfileDBHelper(getApplicationContext());



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (rememberMeCheckbox.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rememberMe", true);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();
                }

                if (username.isEmpty()){
                    usernameInputLayout.setError("Email is required");
                }
                if (password.isEmpty()){
                    passwordInputLayout.setError("Password is required");
                }

                   userDAO = new UserDAOImplementation(LoginActivity.this);
                   user = userDAO.login(username, password);


                    if (user != null){
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        usernameInputLayout.setError("Invalid username or password");
                        passwordInputLayout.setError("Invalid username or password");
                    }

            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        rememberMeCheckbox.setChecked(rememberMe);

        rememberMeCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rememberMe", rememberMeCheckbox.isChecked());
                editor.apply();
            }
        });
    }
}
