package com.busbooking.service;
import com.busbooking.model.*;
public class BusService {
    public void listBuses(){
        System.out.println("\n-- Buses --");
        for(Bus t : DataStore.buses.values()){
            System.out.printf("%s | %s | %s -> %s | Avail: %d | Fare: %.2f\n", t.getBusId(), t.getName(), t.getFrom(), t.getTo(), t.getAvailableSeats(), t.getFarePerSeat());
        }
    }
    public void searchBuses(String from, String to){
        System.out.println("\n-- Search Results --");
        for(Bus t : DataStore.buses.values()){
            if(t.getFrom().equalsIgnoreCase(from) && t.getTo().equalsIgnoreCase(to)){
                System.out.printf("%s | %s | Avail: %d | Fare: %.2f\n", t.getBusId(), t.getName(), t.getAvailableSeats(), t.getFarePerSeat());
            }
        }
    }
    public boolean addBus(String id, String name, String from, String to, int seats, double fare){
        if(DataStore.buses.containsKey(id)) return false;
        DataStore.buses.put(id, new Bus(id,name,from,to,seats,fare));
        DataStore.saveAll();
        return true;
    }
    public boolean removeBus(String id){
        // don't remove if bookings exist for bus
        for(Booking b : DataStore.bookings.values()){
            if(b.getBusId().equals(id)) return false;
        }
        if(DataStore.buses.remove(id)!=null){ DataStore.saveAll(); return true; }
        return false;
    }
    public Bus getBusById(String id){ return DataStore.buses.get(id); }
}
