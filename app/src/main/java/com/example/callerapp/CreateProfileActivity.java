package com.example.callerapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.callerapp.DAO.ProfileDAO;
import com.example.callerapp.DAOImplementation.ProfileDAOImplementation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CreateProfileActivity extends AppCompatActivity {

    private ProfileDAO profileDAO;

    private TextInputEditText fullnameEditText, phoneEditText, emailEditText, addressEditText;
    private TextInputLayout fullnameInputLayout, phoneInputLayout, emailInputLayout, addressPasswordInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        // Initialize views
        fullnameInputLayout = findViewById(R.id.editTextName);
        phoneInputLayout = findViewById(R.id.editTextPhone);
        emailInputLayout = findViewById(R.id.editTextEmail);
        addressPasswordInputLayout = findViewById(R.id.editTextAddress);
        fullnameEditText = (TextInputEditText) fullnameInputLayout.getEditText();
        phoneEditText = (TextInputEditText) phoneInputLayout.getEditText();
        emailEditText = (TextInputEditText) emailInputLayout.getEditText();
        addressEditText = (TextInputEditText) addressPasswordInputLayout.getEditText();

        Button saveButton = findViewById(R.id.buttonSave);
        Button cancelButton = findViewById(R.id.buttonCancel);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get input values
                String name = fullnameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();

                // Validate input
                if (name.isEmpty()){
                    fullnameInputLayout.setError("Fullname is required");
                    return;
                }
                if (phone.isEmpty()){
                    phoneInputLayout.setError("Phone is required");
                    return;
                }
                if (email.isEmpty()){
                    emailInputLayout.setError("Email is required");
                    return;
                }
                if (address.isEmpty()){
                    addressPasswordInputLayout.setError("Address is required");
                    return;
                }

                    profileDAO = new ProfileDAOImplementation(CreateProfileActivity.this);
                    Profile profile = new Profile(name, phone, email, address);
                    if (profileDAO.addProfile(profile) != -1) {
                        Toast.makeText(CreateProfileActivity.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateProfileActivity.this, "Error saving profile", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close the current activity when Cancel is clicked
                finish();
            }
        });

    }
}