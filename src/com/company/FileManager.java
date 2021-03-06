package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    private String Path, Path2, Master, Error, Error2;
    private final ArrayList<String> ThreadsId = new ArrayList<>();
    private  ArrayList<ArrayList<String>> Relations = new ArrayList<>();
    private  ArrayList<String> innerRelations;
    private final ArrayList<Long> Timings = new ArrayList<>();

    public ArrayList<ArrayList<String>> getRelations() {
        return Relations;
    }

    public ArrayList<Long> getTimings() {
        return Timings;
    }

    public String getMaster() {
        return Master;
    }

    public ArrayList<String> getThreadsId() {
        return ThreadsId;
    }

    public FileManager(String path, String path2) {
        this.Path = path;
        this.Path2 = path2;
        try { ReadFile(); }catch (Exception e){ e.printStackTrace(); }
        try { ReadFile2(); }catch (Exception e){ e.printStackTrace(); }
    }

    public void ReadFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(Path));
        try {
            String line = br.readLine();

            while (line != null) {
                if(!line.contains("waitfor"))
                    Master = line;
                else {
                    String[] Words = line.split("[ ,]+");
                    for (int i = 0; i< Words.length; i++)
                        if (Words[i].contains("P") && i==0)
                            ThreadsId.add(Words[i]);

                    String[] Words2 = line.split("[ ]+");
                    for (int i = 1; i< Words2.length; i++){
                        if (!Words2[i].contains("waitfor")) {
                            innerRelations = new ArrayList<>();
                            String ThreadName = "";
                            for (int j = 0; j < Words2[i].length(); j++){
                                if(String.valueOf(Words2[i].charAt(j)).equals(",")) {
                                    innerRelations.add(ThreadName);
                                    ThreadName = "";
                                }else {
                                    ThreadName += Words2[i].charAt(j);
                                }
                            }
                            innerRelations.add(ThreadName);
                        }
                    }
                    Relations.add(innerRelations);
                }
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    public void ReadFile2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(Path2));
        try {
            String line = br.readLine();

            while (line != null) {
                String[] Words = line.split("[ ]+");
                if(Words.length <= 2) {
                    if (Words.length == 1) Timings.add(0L);
                    else if (Words.length == 2) Timings.add(Long.parseLong(Words[1]));
                    line = br.readLine();
                }else{
                    Error2 = "File is not in valid form!";
                    break;
                }
            }

        } finally {
            br.close();
        }
    }
}
