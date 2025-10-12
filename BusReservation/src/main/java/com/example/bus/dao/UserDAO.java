package com.example.bus.dao;

import com.example.bus.*;
import com.example.bus.models.User;
import java.sql.*;

public class UserDAO {
    public static boolean register(String name, String email, String passwordHash, String phone) {
        String sql = "INSERT INTO users (name, email, password_hash, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, passwordHash);
            ps.setString(4, phone);
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Email already registered.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User login(String email, String passwordHash) {
        String sql = "SELECT user_id, name, email, phone FROM users WHERE email = ? AND password_hash = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("user_id"), rs.getString("name"),
                                    rs.getString("email"), rs.getString("phone"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
