package com.example;



import java.util.concurrent.Semaphore;

public class DBConnectionPool {
    private final Semaphore connectionPool = new Semaphore(3);

    public void databaseConnection(int threadId) {
        try {
            long startWaiting = System.currentTimeMillis();
            System.out.println(String.format("Thread %d is Connecting...",threadId));
            connectionPool.acquire(); // Wait for available connection
            long endWaiting = System.currentTimeMillis();
            System.out.println("Thread " + threadId + ": Connected to database ("+(endWaiting-startWaiting)+"ms)");
            Thread.sleep(2000); // Simulate database work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Thread " + threadId + ": Disconnecting");
            connectionPool.release(); // Release connection
        }
    }
}
