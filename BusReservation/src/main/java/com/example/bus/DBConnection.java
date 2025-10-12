package com.example.bus;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {
    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream in = DBConnection.class.getResourceAsStream("/db.properties")) {
            Properties props = new Properties();
            props.load(in);
            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.err.println("Failed to load DB properties: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
