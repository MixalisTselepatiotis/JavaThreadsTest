package com.company;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        FileManager fileManager = new FileManager("C:\\Files\\p_precedence.txt", "C:\\Files\\p_timings.txt");
        ArrayList<String> ThreadsId = fileManager.getThreadsId();
        ArrayList<Long> Timings = fileManager.getTimings();
        //String Master = fileManager.getMaster();

        ExecutorService executorService = Executors.newFixedThreadPool(ThreadsId.size());
        for(int i = 0; i<ThreadsId.size(); i++){
            int finalI = i;
            executorService.execute(() -> { Singleton.getInstance().Dependency(""+ThreadsId.get(finalI), Timings.get(finalI)); });
        }
    }
}









