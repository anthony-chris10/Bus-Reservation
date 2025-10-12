package com.example.bus.models;

public class Bus {
    private int busId;
    private String busName;
    private String busType;
    private String source;
    private String destination;
    private int totalSeats;
    private int availableSeats;
    private double farePerSeat;

    public Bus(int busId, String busName, String busType, String source, String destination,
               int totalSeats, int availableSeats, double farePerSeat) {
        this.busId = busId; this.busName = busName; this.busType = busType; this.source = source;
        this.destination = destination; this.totalSeats = totalSeats; this.availableSeats = availableSeats;
        this.farePerSeat = farePerSeat;
    }

    public int getBusId(){return busId;}
    public String getBusName(){return busName;}
    public String getBusType(){return busType;}
    public String getSource(){return source;}
    public String getDestination(){return destination;}
    public int getTotalSeats(){return totalSeats;}
    public int getAvailableSeats(){return availableSeats;}
    public double getFarePerSeat(){return farePerSeat;}
}
