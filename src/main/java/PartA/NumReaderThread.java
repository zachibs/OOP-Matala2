package PartA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A Thread class that reads the number of lines from a given file.
 *
 */
public class NumReaderThread extends Thread {
    /**
     * The name of the file to read.
     */
    String fileName;
    /**
     * The number of lines in the file.
     */
    int numOfLines;

    /**
     * Constructor for the class, initializes the file name.
     *
     * @param fileName the name of the file to read
     */
    public NumReaderThread(String fileName){
        super();
        this.fileName = fileName;
        this.numOfLines = 0;
    }

    /**
     * Reads the number of lines in the file specified by the fileName field
     * and sets the numOfLines field to that value.
     *
     * 
     */
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

    /**
     * Returns the number of lines in the file
     *
     * @return the number of lines in the file
     */
    public int getNumOfLines(){
        return this.numOfLines;
    }
}
