package com.example;

public class App {
    public static void main(String[] args) throws InterruptedException {

        // ==========================================
        // Task 1: Bank Account
        // ==========================================
        System.out.println("--- Task 1: Bank Account ---");
        BankAccount account = new BankAccount();
        Thread bankT1 = new Thread(() -> {
            account.withdraw(1200);
        });
        Thread bankT2 = new Thread(() -> {
            account.deposit(500);
        });
        
        // Daemon setup from your snippet
        bankT1.setDaemon(true); 
        bankT2.setDaemon(false);
        
        bankT1.start();
        bankT2.start();
        bankT1.join();
        bankT2.join();
        System.out.println("Final balance: " + account.getBalance());
        System.out.println();


        // ==========================================
        // Bonus: DB Connection Pool
        // ==========================================
        System.out.println("--- DB Connection Pool Demo ---");
        DBConnectionPool dbConnectionPool = new DBConnectionPool();
        // Start 10 threads, but only 3 can connect simultaneously
        for (int i = 0; i < 3; i++) {
            final int threadId = i;
            new Thread(() -> dbConnectionPool.databaseConnection(threadId)).start();    
        }
        // Sleep to let DB demo finish printing before next task (Optional)
        Thread.sleep(3000); 
        System.out.println();


        // ==========================================
        // Task 2: Producer Consumer
        // ==========================================
        System.out.println("--- Task 2: Producer Consumer ---");
        ProducerConsumer pc = new ProducerConsumer();
        for(int i = 0; i < 2; i++) {
            pc.produce(i); // Using your existing produce(int) method
            new Thread(() -> {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(2000); // Allow time for consumption
        System.out.println();


        // ==========================================
        // Counting Threads (Odd/Even)
        // ==========================================
        System.out.println("--- Counting Threads Demo ---");
        CountingThreads counter = new CountingThreads();

        // Renamed to avoid conflict with BankAccount threads
        Thread counterT1 = new Thread(() -> {
            counter.Odd();
        }, "Odd-Thread");

        Thread counterT2 = new Thread(() -> {
            counter.Even();
        }, "Even-Thread");

        counterT1.start();
        counterT2.start();

        try {
            counterT1.join();
            counterT2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();


        // ==========================================
        // Task 3: Reader-Writer Problem
        // ==========================================
        System.out.println("--- Task 3: Reader-Writer Problem ---");
        // Ensure you have created the ReaderWriter class provided previously
        ReaderWriter rw = new ReaderWriter();
        rw.write("Key1", "Init");

        new Thread(() -> {
            for (int i = 0; i < 1; i++) rw.write("Key1", "Update-" + i);
        }).start();

        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1; j++) rw.read("Key1");
            }).start();
        }
        Thread.sleep(3000); // Wait for RW demo to finish
        System.out.println();


        // ==========================================
        // Task 4: Dining Philosophers
        // ==========================================
        System.out.println("--- Task 4: Dining Philosophers ---");
        // Ensure you have created the DiningPhilosophers class provided previously
        DiningPhilosophers.startSimulation();
    }
}