package com.example.callerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.callerapp.DAO.ProfileDAO;
import com.example.callerapp.DAOImplementation.ProfileDAOImplementation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateProfileActivity extends AppCompatActivity {

    private TextInputEditText fullnameEditText, phoneEditText, emailEditText, addressEditText;
    private TextInputLayout fullnameInputLayout, phoneInputLayout, emailInputLayout, addressPasswordInputLayout;
    private ProfileDAO profileDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        profileDAO = new ProfileDAOImplementation(this);


        // Retrieve profile details from intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String profileId = intent.getStringExtra("PROFILE_ID");
            String name = intent.getStringExtra("PROFILE_NAME");
            String email = intent.getStringExtra("PROFILE_EMAIL");
            String phone = intent.getStringExtra("PROFILE_PHONE");
            String address = intent.getStringExtra("PROFILE_ADDRESS");

            // Initialize views
            fullnameInputLayout = findViewById(R.id.editTextName);
            phoneInputLayout = findViewById(R.id.editTextPhone);
            emailInputLayout = findViewById(R.id.editTextEmail);
            addressPasswordInputLayout = findViewById(R.id.editTextAddress);
            fullnameEditText = (TextInputEditText) fullnameInputLayout.getEditText();
            phoneEditText = (TextInputEditText) phoneInputLayout.getEditText();
            emailEditText = (TextInputEditText) emailInputLayout.getEditText();
            addressEditText = (TextInputEditText) addressPasswordInputLayout.getEditText();

            fullnameEditText.setText(name);
            emailEditText.setText(email);
            phoneEditText.setText(phone);
            addressEditText.setText(address);

            Button updateButton = findViewById(R.id.buttonEdit);
            Button cancelButton = findViewById(R.id.buttonCancel);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve updated profile details from input fields
                    String name = fullnameEditText.getText().toString().trim();
                    String email = emailEditText.getText().toString().trim();
                    String phone = phoneEditText.getText().toString().trim();
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


                    // Create a new Profile object with updated details
                    Profile updatedProfile = new Profile(Long.parseLong(profileId), name, phone, email, address);

                    // Update the profile in the database
                    int isSuccess = profileDAO.updateProfile(updatedProfile, Long.parseLong(profileId));

                    if (isSuccess > 0) {
                        Toast.makeText(UpdateProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        // Close the activity after successful update
                        finish();
                    } else {
                        Toast.makeText(UpdateProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close the activity when Cancel is clicked
                    finish();
                }
            });
        }
    }
}
