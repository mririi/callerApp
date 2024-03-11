package com.example.callerapp.DAOImplementation;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.callerapp.DAO.ProfileDAO;
import com.example.callerapp.Profile;
import com.example.callerapp.ProfileDBHelper;

import java.util.ArrayList;
import java.util.List;

public class ProfileDAOImplementation implements ProfileDAO {

    ProfileDBHelper dbHelper;

    public ProfileDAOImplementation(Context context) {
        this.dbHelper = new ProfileDBHelper(context);
    }

    @Override
    public List<Profile> getProfiles() {
        List<Profile> profileList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + dbHelper.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_NAME));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_PHONE));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_EMAIL));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_ADDRESS));

                Profile profile = new Profile(name, phone, email, address);
                profileList.add(profile);
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return profileList;
    }

    public Profile getProfileById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbHelper.TABLE_NAME + " WHERE " + dbHelper.COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        Profile profile = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_NAME));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_PHONE));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_EMAIL));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_ADDRESS));
            profile = new Profile(name, phone, email, address);
            cursor.close();
            return profile;
        }
        db.close();
        return null;
    }

    @Override
    public long addProfile(Profile profile) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_NAME, profile.getName());
        values.put(dbHelper.COLUMN_PHONE, profile.getPhone());
        values.put(dbHelper.COLUMN_EMAIL, profile.getEmail());
        values.put(dbHelper.COLUMN_ADDRESS, profile.getAddress());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(dbHelper.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    @Override
    public int updateProfile(Profile profile, long id) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_NAME, profile.getName());
        values.put(dbHelper.COLUMN_PHONE, profile.getPhone());
        values.put(dbHelper.COLUMN_EMAIL, profile.getEmail());
        values.put(dbHelper.COLUMN_ADDRESS, profile.getAddress());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRow = db.update(dbHelper.TABLE_NAME, values, dbHelper.COLUMN_ID + " = " + id, null);
        db.close();
        return updatedRow;

    }

    @Override
    public int deleteProfile(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRow = db.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_ID + " = " + id, null);
        db.close();
        return deletedRow;
    }
}
