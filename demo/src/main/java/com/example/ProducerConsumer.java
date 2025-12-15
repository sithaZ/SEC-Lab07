package com.example;



import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int capacity = 5;
    private final Object lock = new Object();

    public void produce(int item) throws InterruptedException {
        synchronized (lock) {
            while (buffer.size() == capacity) {
                lock.wait(); // Wait until buffer has space
            }
            buffer.offer(item);
            System.out.println("Produced: " + item);
            lock.notifyAll(); // Notify waiting consumers
        }
    }

    public int consume() throws InterruptedException {
        synchronized (lock) {
            while (buffer.isEmpty()) {
                lock.wait(); // Wait until buffer has items
            }
            int item = buffer.poll();
            System.out.println("Consumed: " + item);
            lock.notifyAll(); // Notify waiting producers
            return item;
        }
    }
}
