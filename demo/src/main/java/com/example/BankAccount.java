package com.example;

import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private double balance = 1000.0;
    private final ReentrantLock lock = new ReentrantLock();
    
    public void withdraw(double amount) {
        lock.lock();  // Acquire lock
        try {
            if (amount <= balance) {
                System.out.println("Withdrawing " + amount);
                balance -= amount;
                System.out.println("New balance: " + balance);
            } else {
                System.out.println("Insufficient funds for withdrawal of " + amount);
            }
        } finally {
            lock.unlock(); // Always release lock
        }
    }

    public void deposit(double amount) {
        lock.lock();  // Acquire lock
        try {
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive");
                return;
            }
            System.out.println("Depositing " + amount);
            balance += amount;
            System.out.println("New balance: " + balance);
        } finally {
            lock.unlock(); // Release lock
        }
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
