package com.busbooking.service;
import com.busbooking.model.*;
public class BookingService {
    public boolean bookTicket(String username, String busId, int seats){
        Bus t = DataStore.buses.get(busId);
        if(t==null) return false;
        synchronized(t){
            if(!t.reserveSeats(seats)) return false;
            Booking b = new Booking(username, busId, seats);
            DataStore.bookings.put(b.getBookingId(), b);
            DataStore.saveAll();
            return true;
        }
    }
    public void viewUserBookings(String username){
        System.out.println("\n-- My Bookings --");
        for(Booking b : DataStore.bookings.values()){
            if(b.getUsername().equals(username)){
                System.out.printf("%s | Bus: %s | Seats: %d\n", b.getBookingId(), b.getBusId(), b.getSeats());
            }
        }
    }
    public void viewAllBookings(){
        System.out.println("\n-- All Bookings --");
        for(Booking b : DataStore.bookings.values()){
            System.out.printf("%s | User: %s | Bus: %s | Seats: %d\n", b.getBookingId(), b.getUsername(), b.getBusId(), b.getSeats());
        }
    }
    public boolean cancelBooking(String bookingId, String username){
        Booking b = DataStore.bookings.get(bookingId);
        if(b==null) return false;
        if(!b.getUsername().equals(username) && !username.equals("admin")) return false;
        Bus t = DataStore.buses.get(b.getBusId());
        if(t!=null){ t.releaseSeats(b.getSeats()); }
        DataStore.bookings.remove(bookingId);
        DataStore.saveAll();
        return true;
    }
}
