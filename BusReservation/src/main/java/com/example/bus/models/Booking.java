package com.example.bus.models;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int userId;
    private int busId;
    private int numSeats;
    private double totalAmount;
    private Timestamp bookingDate;

    public Booking(int bookingId, int userId, int busId, int numSeats, double totalAmount, Timestamp bookingDate){
        this.bookingId = bookingId; this.userId = userId; this.busId = busId; this.numSeats = numSeats;
        this.totalAmount = totalAmount; this.bookingDate = bookingDate;
    }

    public int getBookingId(){return bookingId;}
    public int getUserId(){return userId;}
    public int getBusId(){return busId;}
    public int getNumSeats(){return numSeats;}
    public double getTotalAmount(){return totalAmount;}
    public Timestamp getBookingDate(){return bookingDate;}
}
