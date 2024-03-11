package com.example.callerapp.DAOImplementation;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.callerapp.DAO.UserDAO;
import com.example.callerapp.ProfileDBHelper;
import com.example.callerapp.User;

public class UserDAOImplementation implements UserDAO {
    ProfileDBHelper dbHelper;

    public UserDAOImplementation(Context context) {
        this.dbHelper = new ProfileDBHelper(context);
    }
    @Override
    public boolean register(User user) {
        //check if the user is already in the database
        User userExistance = getUserByUsername(user.getUsername());
        if (userExistance != null) {
            //return User already exists code
            return false;
        }
        //open the database and use insert method
        ContentValues values = new ContentValues();
        values.put("fullname", user.getFullname());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        dbHelper.getWritableDatabase().insert("users", null, values);
        //close the database
        dbHelper.closeDB();
        //return the result
        return true;
    }

    @Override
    public User login(String username, String password) {
        //open the data base and find the user by username and password
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") User user = new User(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("fullname")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("password")));
            //close the database
            dbHelper.closeDB();
            //return the user
            return user;
        }
        //close the database
        dbHelper.closeDB();
        //return null
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        //open the data base and find the user by username and password
        String sql = "SELECT * FROM users WHERE username = ?";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, new String[]{username});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") User user = new User(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("fullname")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("password")));
            //close the database
            dbHelper.closeDB();
            //return the user
            return user;
        }
        //close the database
        dbHelper.closeDB();
        return null;
    }
}
