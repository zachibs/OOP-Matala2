package PartA;

import java.util.concurrent.Callable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A callable class that reads the number of lines from a given file.
 * Implements the Callable interface, meaning it can be used in multi-threading.
 *
 */
public class NumReaderCallable implements Callable {
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
    public NumReaderCallable(String fileName){
        this.fileName = fileName;
        this.numOfLines = 0;
    }

    /**
     * Reads the number of lines in the file specified by the fileName field
     * and sets the numOfLines field to that value.
     *
     * @return the number of lines in the file
     * @throws Exception if there is an error while reading the file
     */
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
