package com.company;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Singleton {
    private static final Singleton instance = new Singleton();
    private final ArrayList<String> currentThread = new ArrayList<>();
    private final Semaphore semaphore = new Semaphore(3);

    private Singleton() { }

    public static Singleton getInstance() {
        return instance;
    }

    public void Dependency(String Name, long sleep) {

        Thread.currentThread().setName(Name);
        synchronized (currentThread) {
            if (!currentThread.isEmpty())
                System.out.println("Try to enter " + Thread.currentThread().getName() + " and wait " +currentThread );
        }

        try{ semaphore.acquire(); }catch (Exception ignored){ }finally{ semaphore.release(); }

        synchronized (currentThread) {
            System.out.println("Entered "+Thread.currentThread().getName());
            currentThread.add(Name);

            /*try {
                currentThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            try {
                Thread.sleep(sleep);
            } catch (Exception e) { }
        }

        synchronized (currentThread) {
            currentThread.remove(0);
            System.out.println("Out " + Thread.currentThread().getName());
            semaphore.release();
            //currentThread.notify();
        }
    }
}
