package com.busbooking.service;
import java.util.Scanner;
public class PaymentService {
    public boolean pay(double amount, Scanner sc){
        System.out.println("-- Payment (dummy) --");
        System.out.print("Enter card number (any digits): "); sc.nextLine();
        System.out.print("Enter expiry (MM/YY): "); sc.nextLine();
        System.out.print("Enter CVV: "); sc.nextLine();
        System.out.println("Processing... (simulated)");
        System.out.println("Payment successful.");
        return true;
    }
}
