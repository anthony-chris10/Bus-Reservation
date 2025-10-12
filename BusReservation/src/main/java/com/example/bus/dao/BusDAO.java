package com.example.bus.dao;

import com.example.bus.*;
import com.example.bus.models.Bus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {
    public static boolean addBus(String busName, String busType, String source, String destination,
                                 int totalSeats, double farePerSeat) {
        String sql = "INSERT INTO buses (bus_name, bus_type, source, destination, total_seats, available_seats, fare_per_seat) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, busName);
            ps.setString(2, busType);
            ps.setString(3, source);
            ps.setString(4, destination);
            ps.setInt(5, totalSeats);
            ps.setInt(6, totalSeats);
            ps.setDouble(7, farePerSeat);
            ps.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static List<Bus> listBuses() {
        String sql = "SELECT * FROM buses";
        List<Bus> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Bus(rs.getInt("bus_id"), rs.getString("bus_name"), rs.getString("bus_type"),
                        rs.getString("source"), rs.getString("destination"), rs.getInt("total_seats"),
                        rs.getInt("available_seats"), rs.getDouble("fare_per_seat")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static Bus getBusById(int busId) {
        String sql = "SELECT * FROM buses WHERE bus_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, busId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Bus(rs.getInt("bus_id"), rs.getString("bus_name"), rs.getString("bus_type"),
                            rs.getString("source"), rs.getString("destination"), rs.getInt("total_seats"),
                            rs.getInt("available_seats"), rs.getDouble("fare_per_seat"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public static boolean updateBus(int busId, String busName, String busType, String source, String destination,
                                    int totalSeats, double farePerSeat) {
        String sql = "UPDATE buses SET bus_name=?, bus_type=?, source=?, destination=?, total_seats=?, available_seats=?, fare_per_seat=? WHERE bus_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, busName);
            ps.setString(2, busType);
            ps.setString(3, source);
            ps.setString(4, destination);
            ps.setInt(5, totalSeats);
            ps.setInt(6, totalSeats); // resets available seats to total; admin can adjust later
            ps.setDouble(7, farePerSeat);
            ps.setInt(8, busId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}
