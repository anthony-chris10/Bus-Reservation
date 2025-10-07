package com.busbooking.service;
import com.busbooking.model.*;
public class UserService {
    public boolean register(String username, String fullName, String password){
        if(DataStore.users.containsKey(username)) return false;
        DataStore.users.put(username, new User(username, fullName, password, false));
        DataStore.saveAll();
        return true;
    }
    public User login(String username, String password){
        User u = DataStore.users.get(username);
        if(u!=null && u.checkPassword(password)) return u;
        return null;
    }
}
