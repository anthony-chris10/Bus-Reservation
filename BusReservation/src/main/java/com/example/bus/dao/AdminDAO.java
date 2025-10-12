package com.example.bus.dao;

import com.example.bus.*;
import java.sql.*;

public class AdminDAO {
    public static boolean login(String username, String passwordHash) {
        String sql = "SELECT admin_id FROM admins WHERE username = ? AND password_hash = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
