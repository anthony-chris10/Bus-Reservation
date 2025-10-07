package com.busbooking;
import com.busbooking.model.*;
import com.busbooking.service.*;
import java.util.Scanner;

public class Application {
    private Scanner sc;
    private UserService userService = new UserService();
    private BusService busService = new BusService();
    private BookingService bookingService = new BookingService();
    // private AdminService adminService = new AdminService();
    private PaymentService paymentService = new PaymentService();
    private User currentUser = null;

    public Application(Scanner sc){
        this.sc = sc;
    }

    public void start(){
        while(true){
            System.out.println("\n=== Bus Booking System ===");
            System.out.println("1. Register\n2. Login\n3. Exit");
            System.out.print("Choose: ");
            String opt = sc.nextLine().trim();
            if(opt.equals("1")){
                register();
            } else if(opt.equals("2")){
                login();
            } else if(opt.equals("3")){
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void register(){
        System.out.println("-- Register --");
        System.out.print("Enter username: "); String u = sc.nextLine().trim();
        System.out.print("Enter full name: "); String name = sc.nextLine().trim();
        System.out.print("Enter password: "); String p = sc.nextLine().trim();
        boolean ok = userService.register(u, name, p);
        System.out.println(ok?"Registered successfully":"Registration failed (username may exist).");
    }

    private void login(){
        System.out.println("-- Login --");
        System.out.print("Username: "); String u = sc.nextLine().trim();
        System.out.print("Password: "); String p = sc.nextLine().trim();
        User user = userService.login(u, p);
        if(user==null){
            System.out.println("Login failed.");
            return;
        }
        currentUser = user;
        if(user.isAdmin()){
            adminMenu();
        } else {
            userMenu();
        }
    }

    private void userMenu(){
        while(true){
            System.out.println("\n-- User Menu ("+currentUser.getUsername()+") --");
            System.out.println("1. View Buses\n2. Search Buses\n3. Book Ticket\n4. My Bookings\n5. Cancel Booking\n6. Logout");
            System.out.print("Choose: "); String opt=sc.nextLine().trim();
            switch(opt){
                case "1": busService.listBuses(); break;
                case "2": searchBuses(); break;
                case "3": bookTicket(); break;
                case "4": bookingService.viewUserBookings(currentUser.getUsername()); break;
                case "5": cancelBooking(); break;
                case "6": currentUser=null; return;
                default: System.out.println("Invalid option."); 
            }
        }
    }

    private void adminMenu(){
        while(true){
            System.out.println("\n-- Admin Menu --");
            System.out.println("1. Add Bus\n2. Remove Bus\n3. List Buses\n4. View All Bookings\n5. Logout");
            System.out.print("Choose: "); String opt=sc.nextLine().trim();
            switch(opt){
                case "1": addBus(); break;
                case "2": removeBus(); break;
                case "3": busService.listBuses(); break;
                case "4": bookingService.viewAllBookings(); break;
                case "5": currentUser=null; return;
                default: System.out.println("Invalid option."); 
            }
        }
    }

    private void searchBuses(){
        System.out.print("From: "); String from=sc.nextLine().trim();
        System.out.print("To: "); String to=sc.nextLine().trim();
        busService.searchBuses(from, to);
    }

    private void bookTicket(){
        busService.listBuses();
        System.out.print("Enter Bus ID to book: "); String id = sc.nextLine().trim();
        Bus t = busService.getBusById(id);
        if(t==null){ System.out.println("Bus not found."); return; }
        System.out.print("Enter number of seats: "); int seats = Integer.parseInt(sc.nextLine().trim());
        double amount = seats * t.getFarePerSeat();
        System.out.println("Amount: " + amount);
        System.out.print("Proceed to payment? (y/n): "); String p = sc.nextLine().trim();
        if(!p.equalsIgnoreCase("y")){ System.out.println("Booking cancelled."); return; }
        boolean paid = paymentService.pay(amount, sc);
        if(!paid){ System.out.println("Payment failed."); return; }
        boolean ok = bookingService.bookTicket(currentUser.getUsername(), t.getBusId(), seats);
        System.out.println(ok?"Booking successful":"Booking failed (maybe insufficient seats).");
    }

    private void cancelBooking(){
        System.out.print("Enter Booking ID to cancel: "); String bid = sc.nextLine().trim();
        boolean ok = bookingService.cancelBooking(bid, currentUser.getUsername());
        System.out.println(ok?"Cancelled":"Cancel failed (not found or not your booking).");
    }

    private void addBus(){
        System.out.print("Bus ID: "); String id=sc.nextLine().trim();
        System.out.print("Name: "); String name=sc.nextLine().trim();
        System.out.print("From: "); String from=sc.nextLine().trim();
        System.out.print("To: "); String to=sc.nextLine().trim();
        System.out.print("Total seats: "); int seats=Integer.parseInt(sc.nextLine().trim());
        System.out.print("Fare per seat: "); double fare=Double.parseDouble(sc.nextLine().trim());
        boolean ok = busService.addBus(id,name,from,to,seats,fare);
        System.out.println(ok?"Bus added":"Failed (maybe ID exists).");
    }

    private void removeBus(){
        System.out.print("Enter Bus ID to remove: "); String id=sc.nextLine().trim();
        boolean ok = busService.removeBus(id);
        System.out.println(ok?"Removed":"Remove failed (not found or has bookings).");
    }
}
