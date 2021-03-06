package com.company;

import java.util.ArrayList;
import java.util.concurrent.*;

class ThreadManager {

    FileManager fileManager = new FileManager("C:\\Files\\p_precedence.txt", "C:\\Files\\p_timings.txt");
    ArrayList<String> ThreadsId = fileManager.getThreadsId();
    ArrayList<Long> Timings = fileManager.getTimings();
    String Master = fileManager.getMaster();
    public static ArrayList<Long> Done = new ArrayList<>();

    public void print(String m) {
        System.out.println(m);
    }

    public void poolRun() throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newFixedThreadPool(ThreadsId.size());
        pool.submit(new Master(Master)).get();

        for (int i = 1; i < ThreadsId.size(); i++) {
            pool.execute(new Two(ThreadsId.get(i)));
        }


        //pool.submit(new One(Threads[0])).get();
        pool.shutdown();
    }

    public class Master implements Callable<Boolean> {

        String Thr = "";
        public Master(String thr) {
            this.Thr = thr;
        }

        public Boolean call() throws Exception {
            Thread.currentThread().setName(""+Thr);
            print(""+ Thr);
            Thread.sleep(1000);
            return true;
        }
    }

    public class Two implements Runnable {
        String id;

        public Two(String id) {
            this.id = id;
        }

        @Override
        public void run() {

        }

        public void Execute(int n){
            Semaphore semaphore = new Semaphore(n);
            Thread.currentThread().setName(""+id);
            print(""+id);
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //print(""+id+" Finished !"+Thread.currentThread().getName());
        }
    }
    //https://stackoverflow.com/questions/15976696/how-to-check-if-a-thread-is-running-in-the-executorservice-thread-pool
}
