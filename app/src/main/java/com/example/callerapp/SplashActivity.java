package com.example.callerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        // Check if "Remember Me" is checked and skip login page if true
        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        Class<?> destinationClass = rememberMe ? HomeActivity.class : LoginActivity.class;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start your main activity
                Intent intent = new Intent(SplashActivity.this, destinationClass);
                startActivity(intent);
                finish();  // Optional: Close the splash screen activity
            }
        }, 3000);
    }
}