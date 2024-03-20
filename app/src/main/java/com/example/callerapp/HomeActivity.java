package com.example.callerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProfileAdapter profileAdapter;
    List<Profile> profileList;


    private static final int REQUEST_PHONE_CALL = 1;

    SearchView searchView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProfileDBHelper dbHelper = new ProfileDBHelper(getApplicationContext());
        profileList = dbHelper.getAllProfiles();
        profileAdapter = new ProfileAdapter(profileList,this);
        recyclerView.setAdapter(profileAdapter);
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });

        Intent i = getIntent();
        if (i.hasExtra("newProfile")) {
            Profile newProfile = (Profile) i.getSerializableExtra("newProfile");
            if (newProfile != null) {
                profileList.add(newProfile);
                profileAdapter.notifyDataSetChanged();
            }
        }
    }

    public void onAdd(View view) {
            Intent intent = new Intent(HomeActivity.this, CreateProfileActivity.class);
            startActivity(intent);
    }

    public void onLogout(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberMe", false);
        editor.apply();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void filterData(String text) {
        List<Profile> filteredList = new ArrayList<>();
        for (Profile item : profileList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        profileAdapter = new ProfileAdapter(filteredList,this);
        recyclerView.setAdapter(profileAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        ProfileDBHelper dbHelper = new ProfileDBHelper(getApplicationContext());
        profileList.clear(); // Clear the existing data
        profileList.addAll(dbHelper.getAllProfiles()); // Fetch new data from the database
        profileAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
    }



    }
