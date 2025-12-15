package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReaderWriter {
    private final Map<String, String> data = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void write(String key, String value) {
        lock.writeLock().lock(); // Exclusive access
        try {
            System.out.println(Thread.currentThread().getName() + " is writing: " + key + " = " + value);
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            data.put(key, value);
            System.out.println(Thread.currentThread().getName() + " finished writing.");
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String read(String key) {
        lock.readLock().lock(); // Shared access
        try {
            System.out.println(Thread.currentThread().getName() + " is reading " + key);
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            String value = data.get(key);
            System.out.println(Thread.currentThread().getName() + " read value: " + value);
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }
}