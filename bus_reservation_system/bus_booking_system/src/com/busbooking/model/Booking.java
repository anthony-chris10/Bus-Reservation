package com.busbooking.model;
import java.io.Serializable;
import java.util.UUID;

public class Booking implements Serializable {
    private String bookingId;
    private String username;
    private String busId;
    private int seats;

    public Booking(String username, String busId, int seats){
        this.bookingId = UUID.randomUUID().toString().substring(0,8);
        this.username = username; this.busId = busId; this.seats = seats;
    }
    public String getBookingId(){ return bookingId; }
    public String getUsername(){ return username; }
    public String getBusId(){ return busId; }
    public int getSeats(){ return seats; }
}
