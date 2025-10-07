package com.busbooking;

import com.busbooking.service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataStore.init(); // load or create sample data
        Scanner sc = new Scanner(System.in);
        Application app = new Application(sc);
        app.start();
        sc.close();
        DataStore.saveAll();
        System.out.println("Exiting. Data saved. Goodbye!");
    }
}
