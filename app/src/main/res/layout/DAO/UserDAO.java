package com.example.callerapp.DAO;

import com.example.callerapp.User;

public interface UserDAO {
    boolean register(User user);
    User login(String username, String password);
    User getUserByUsername(String username);
}
