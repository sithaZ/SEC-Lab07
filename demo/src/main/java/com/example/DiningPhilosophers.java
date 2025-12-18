package com.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DiningPhilosophers {

    public static void startSimulation() {
        int numPhilosophers = 5;
        Philosopher[] philosophers = new Philosopher[numPhilosophers];
        Lock[] forks = new ReentrantLock[numPhilosophers];
        AtomicBoolean running = new AtomicBoolean(true);

        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < numPhilosophers; i++) {
            Lock leftFork = forks[i];
            Lock rightFork = forks[(i + 1) % numPhilosophers];
            philosophers[i] = new Philosopher(i, leftFork, rightFork, running);
            new Thread(philosophers[i], "Philosopher-" + (i + 1)).start();
        }

      
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        running.set(false); 
        
        System.out.println("\n--- Simulation Finished ---");
        for (Philosopher p : philosophers) {
            System.out.println(p.getStats());
        }
    }

    static class Philosopher implements Runnable {
        private final int id;
        private final Lock leftFork;
        private final Lock rightFork;
        private final AtomicBoolean running;
        private int eatCount = 0;

        public Philosopher(int id, Lock leftFork, Lock rightFork, AtomicBoolean running) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
            this.running = running;
        }

        private void action(String action) throws InterruptedException {
            System.out.println("Philosopher " + id + " is " + action);
            Thread.sleep((long) (Math.random() * 100));
        }

        public String getStats() {
            return "Philosopher " + id + " ate " + eatCount + " times.";
        }

        @Override
        public void run() {
            try {
                while (running.get()) {
                    action("thinking"); // Thinking

                    
                    if (leftFork.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            
                            if (rightFork.tryLock(100, TimeUnit.MILLISECONDS)) {
                                try {
                                    action("eating"); // Eating
                                    eatCount++;
                                } finally {
                                    rightFork.unlock();
                                }
                            }
                        } finally {
                            leftFork.unlock();
                        }
                    }
               
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}