package com.example.callerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     RecyclerView recyclerView;
    ProfileAdapter profileAdapter;
    List<Profile> profileList;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.svProfile_main);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProfileDBHelper dbHelper = new ProfileDBHelper(getApplicationContext());
        profileList = dbHelper.getAllProfiles();
        profileAdapter = new ProfileAdapter(profileList,this);
        recyclerView.setAdapter(profileAdapter);

        Intent i = getIntent();
        if (i.hasExtra("newProfile")) {
            Profile newProfile = (Profile) i.getSerializableExtra("newProfile");
            if (newProfile != null) {
                profileList.add(newProfile);
                profileAdapter.notifyDataSetChanged();
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });
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


}
