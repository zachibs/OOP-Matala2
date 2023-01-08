package PartA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NumReaderThread extends Thread {
    String fileName;
    int numOfLines;

    public NumReaderThread(String fileName){
        super();
        this.fileName = fileName;
        this.numOfLines = 0;
    }
    
    @Override
    public void run(){

        List<String> lines;
        Path filePath = Paths.get(this.fileName);
        try{
            lines = Files.readAllLines(filePath);
            this.numOfLines += lines.size();
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public int getNumOfLines(){
        return this.numOfLines;
    }
}
