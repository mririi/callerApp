package com.example.callerapp.DAO;

import com.example.callerapp.Profile;

import java.util.List;

public interface ProfileDAO {

    List<Profile> getProfiles();
    Profile getProfileById(long id);
    long addProfile(Profile profile);
    int updateProfile(Profile profile,long id);
    int deleteProfile(long id);

}
