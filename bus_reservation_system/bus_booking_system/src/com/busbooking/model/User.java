package com.busbooking.model;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String fullName;
    private String password;
    private boolean admin;

    public User(String username, String fullName, String password, boolean admin){
        this.username=username; this.fullName=fullName; this.password=password; this.admin=admin;
    }
    public String getUsername(){ return username; }
    public String getFullName(){ return fullName; }
    public boolean isAdmin(){ return admin; }
    public boolean checkPassword(String p){ return password.equals(p); }
}
