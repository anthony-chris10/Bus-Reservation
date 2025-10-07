package com.busbooking.model;
import java.io.Serializable;

public class Bus implements Serializable {
    private String busId;
    private String name;
    private String from;
    private String to;
    private int totalSeats;
    private int availableSeats;
    private double farePerSeat;

    public Bus(String busId, String name, String from, String to, int totalSeats, double farePerSeat){
        this.busId=busId; this.name=name; this.from=from; this.to=to; this.totalSeats=totalSeats; this.availableSeats=totalSeats; this.farePerSeat=farePerSeat;
    }
    public String getBusId(){ return busId; }
    public String getName(){ return name; }
    public String getFrom(){ return from; }
    public String getTo(){ return to; }
    public int getAvailableSeats(){ return availableSeats; }
    public int getTotalSeats(){ return totalSeats; }
    public double getFarePerSeat(){ return farePerSeat; }
    public boolean reserveSeats(int n){
        if(n<=availableSeats){ availableSeats -= n; return true; } return false;
    }
    public void releaseSeats(int n){ availableSeats += n; if(availableSeats>totalSeats) availableSeats=totalSeats; }
}
