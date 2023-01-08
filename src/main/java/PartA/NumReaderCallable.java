package PartA;

import java.util.concurrent.Callable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NumReaderCallable implements Callable {

    String fileName;
    int numOfLines;

    public NumReaderCallable(String fileName){
        this.fileName = fileName;
        this.numOfLines = 0;
    }

    @Override
    public Object call() throws Exception {
        List<String> lines;
        Path filePath = Paths.get(this.fileName);
        try{
            lines = Files.readAllLines(filePath);
            this.numOfLines += lines.size();
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return this.numOfLines;
    }
    
}
