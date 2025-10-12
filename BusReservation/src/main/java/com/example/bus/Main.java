package com.example.bus;

import com.example.bus.dao.*;
import com.example.bus.models.*;
import java.util.*;
import java.sql.Timestamp;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static int currentUserId = -1;

    public static void main(String[] args) {
        System.out.println("=== Bus Reservation System (MySQL-backed) ===");
        while (true) {
            System.out.println("\n1) Register  2) Login (User)  3) Login (Admin)  4) Exit");
            System.out.print("Choose: ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1": register(); break;
                case "2": userLogin(); break;
                case "3": adminLogin(); break;
                case "4": System.out.println("Goodbye!"); System.exit(0);
                default: System.out.println("Invalid");
            }
        }
    }

    private static void register() {
        System.out.print("Name: "); String name = sc.nextLine().trim();
        System.out.print("Email: "); String email = sc.nextLine().trim();
        System.out.print("Phone: "); String phone = sc.nextLine().trim();
        System.out.print("Password: "); String pw = sc.nextLine().trim();
        String hash = PasswordUtil.sha256(pw);
        boolean ok = UserDAO.register(name, email, hash, phone);
        System.out.println(ok ? "Registered successfully." : "Registration failed.");
    }

    private static void userLogin() {
        System.out.print("Email: "); String email = sc.nextLine().trim();
        System.out.print("Password: "); String pw = sc.nextLine().trim();
        String hash = PasswordUtil.sha256(pw);
        var user = UserDAO.login(email, hash);
        if (user == null) { System.out.println("Login failed."); return; }
        currentUserId = user.getUserId();
        System.out.println("Welcome, " + user.getName());
        userMenu();
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\nUser Menu: 1) View Buses 2) Book Ticket 3) View My Bookings 4) Cancel Booking 5) Logout");
            System.out.print("Choose: ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1": listBuses(); break;
                case "2": bookTicket(); break;
                case "3": viewMyBookings(); break;
                case "4": cancelBooking(); break;
                case "5": currentUserId = -1; return;
                default: System.out.println("Invalid");
            }
        }
    }

    private static void listBuses() {
        var buses = BusDAO.listBuses();
        System.out.println("\nID | Name | Type | From -> To | Available | Fare");
        for (var b : buses) {
            System.out.printf("%d | %s | %s | %s -> %s | %d | %.2f\n",
                    b.getBusId(), b.getBusName(), b.getBusType(), b.getSource(), b.getDestination(),
                    b.getAvailableSeats(), b.getFarePerSeat());
        }
    }

    private static void bookTicket() {
        listBuses();
        try {
            System.out.print("Enter Bus ID: "); int busId = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Number of seats: "); int seats = Integer.parseInt(sc.nextLine().trim());
            var bus = BusDAO.getBusById(busId);
            if (bus == null) { System.out.println("Invalid bus id"); return; }
            if (seats <= 0 || seats > bus.getAvailableSeats()) { System.out.println("Not enough seats"); return; }
            double total = seats * bus.getFarePerSeat();
            boolean ok = BookingDAO.createBooking(currentUserId, busId, seats, total);
            System.out.println(ok ? ("Booked! Total: " + total) : "Booking failed (maybe insufficient seats)");
        } catch (Exception e) { System.out.println("Invalid input"); }
    }

    private static void viewMyBookings() {
        var list = BookingDAO.getBookingsByUser(currentUserId);
        System.out.println("\nBookingID | BusID | Seats | Amount | Date");
        for (var b : list) {
            System.out.printf("%d | %d | %d | %.2f | %s\n",
                    b.getBookingId(), b.getBusId(), b.getNumSeats(), b.getTotalAmount(), b.getBookingDate());
        }
    }

    private static void cancelBooking() {
        viewMyBookings();
        System.out.print("Enter Booking ID to cancel: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean ok = BookingDAO.cancelBooking(id);
            System.out.println(ok ? "Cancelled." : "Cancellation failed.");
        } catch (Exception e) { System.out.println("Invalid input"); }
    }

    private static void adminLogin() {
        System.out.print("Admin Username: "); String user = sc.nextLine().trim();
        System.out.print("Password: "); String pw = sc.nextLine().trim();
        String hash = PasswordUtil.sha256(pw);
        if (!AdminDAO.login(user, hash)) { System.out.println("Admin login failed"); return; }
        adminMenu();
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu: 1) Add Bus 2) Update Bus 3) View Buses 4) Logout");
            System.out.print("Choose: ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1": addBus(); break;
                case "2": updateBus(); break;
                case "3": listBuses(); break;
                case "4": return;
                default: System.out.println("Invalid");
            }
        }
    }

    private static void addBus() {
        try {
            System.out.print("Bus Name: "); String name = sc.nextLine().trim();
            System.out.print("Bus Type (AC, Non-AC, AC Sleeper, Non-AC Sleeper, AC Seater, Non-AC Seater): "); String type = sc.nextLine().trim();
            System.out.print("Source: "); String src = sc.nextLine().trim();
            System.out.print("Destination: "); String dst = sc.nextLine().trim();
            System.out.print("Total Seats: "); int seats = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Fare per seat: "); double fare = Double.parseDouble(sc.nextLine().trim());
            boolean ok = BusDAO.addBus(name, type, src, dst, seats, fare);
            System.out.println(ok ? "Bus added." : "Failed to add bus.");
        } catch (Exception e) { System.out.println("Invalid input"); }
    }

    private static void updateBus() {
        listBuses();
        try {
            System.out.print("Enter Bus ID to update: "); int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("New Bus Name: "); String name = sc.nextLine().trim();
            System.out.print("New Bus Type: "); String type = sc.nextLine().trim();
            System.out.print("Source: "); String src = sc.nextLine().trim();
            System.out.print("Destination: "); String dst = sc.nextLine().trim();
            System.out.print("Total Seats: "); int seats = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Fare per seat: "); double fare = Double.parseDouble(sc.nextLine().trim());
            boolean ok = BusDAO.updateBus(id, name, type, src, dst, seats, fare);
            System.out.println(ok ? "Updated." : "Update failed.");
        } catch (Exception e) { System.out.println("Invalid"); }
    }
}
