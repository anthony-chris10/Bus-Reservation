package com.busbooking.service;
import com.busbooking.model.*;
import java.util.*;
import java.io.*;

public class DataStore {
    public static Map<String,User> users = new HashMap<>();
    public static Map<String,Bus> buses = new HashMap<>();
    public static Map<String,Booking> bookings = new HashMap<>();
    private static final String DATA_DIR = "data";

    public static void init(){
        try { new File(DATA_DIR).mkdirs(); } catch(Exception e){}
        loadObject("users.ser", users);
        loadObject("buses.ser", buses);
        loadObject("bookings.ser", bookings);
        // ensure admin exists
        if(!users.containsKey("admin")){
            users.put("admin", new User("admin","Administrator","admin123", true));
        }
        if(buses.isEmpty()){
            // sample buses
            addSampleBuses();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void loadObject(String file, Map<String,T> map){
        File f = new File(DATA_DIR+File.separator+file);
        if(!f.exists()) return;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
            Object obj = ois.readObject();
            if(obj instanceof Map){
                map.putAll((Map<String,T>)obj);
            }
        } catch(Exception e){ System.out.println("Warning: could not load " + file); }
    }

    public static void saveAll(){
        saveObject("users.ser", users);
        saveObject("buses.ser", buses);
        saveObject("bookings.ser", bookings);
    }

    private static void saveObject(String file, Object obj){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR+File.separator+file))){
            oos.writeObject(obj);
        } catch(Exception e){ System.out.println("Warning: could not save " + file); }
    }

    private static void addSampleBuses(){
        buses.put("TN100", new Bus("TN100","Non Ac sleepper & Seater","Chennai","Bengaluru",100,250.0));
        buses.put("TN00", new Bus("TN200","Non AC Seater","Coimbatore","Chennai",80,300.0));
        buses.put("TN300", new Bus("TN300","Sleepper AC","Madurai","Trichy",60,180.0));
        buses.put("TN400", new Bus("TN400","1 oto 1","Dindigul","chennai",110,200.0));
        buses.put("TN500", new Bus("TN500","Double Tecker","kanyakumari","trichy",160,400.0));
    }
}
