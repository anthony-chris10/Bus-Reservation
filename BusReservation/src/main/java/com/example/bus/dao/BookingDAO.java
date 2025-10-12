package com.example.bus.dao;

import com.example.bus.*;
import com.example.bus.models.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public static boolean createBooking(int userId, int busId, int numSeats, double totalAmount) throws SQLException {
        String insert = "INSERT INTO bookings (user_id, bus_id, num_seats, total_amount) VALUES (?,?,?,?)";
        String updateSeats = "UPDATE buses SET available_seats = available_seats - ? WHERE bus_id = ? AND available_seats >= ?";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psUpdate = conn.prepareStatement(updateSeats)) {
                psUpdate.setInt(1, numSeats);
                psUpdate.setInt(2, busId);
                psUpdate.setInt(3, numSeats);
                int updated = psUpdate.executeUpdate();
                if (updated == 0) {
                    conn.rollback();
                    return false;
                }
            }
            try (PreparedStatement psInsert = conn.prepareStatement(insert)) {
                psInsert.setInt(1, userId);
                psInsert.setInt(2, busId);
                psInsert.setInt(3, numSeats);
                psInsert.setDouble(4, totalAmount);
                psInsert.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static List<Booking> getBookingsByUser(int userId) {
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        List<Booking> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Booking(rs.getInt("booking_id"), rs.getInt("user_id"),
                            rs.getInt("bus_id"), rs.getInt("num_seats"),
                            rs.getDouble("total_amount"), rs.getTimestamp("booking_date")));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static boolean cancelBooking(int bookingId) {
        String get = "SELECT bus_id, num_seats FROM bookings WHERE booking_id = ?";
        String delete = "DELETE FROM bookings WHERE booking_id = ?";
        String restore = "UPDATE buses SET available_seats = available_seats + ? WHERE bus_id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            int busId=0, numSeats=0;
            try (PreparedStatement psGet = conn.prepareStatement(get)) {
                psGet.setInt(1, bookingId);
                try (ResultSet rs = psGet.executeQuery()) {
                    if (rs.next()) {
                        busId = rs.getInt("bus_id");
                        numSeats = rs.getInt("num_seats");
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }
            try (PreparedStatement psDelete = conn.prepareStatement(delete)) {
                psDelete.setInt(1, bookingId);
                psDelete.executeUpdate();
            }
            try (PreparedStatement psRestore = conn.prepareStatement(restore)) {
                psRestore.setInt(1, numSeats);
                psRestore.setInt(2, busId);
                psRestore.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}
