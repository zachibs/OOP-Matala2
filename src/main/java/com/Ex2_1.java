package com;

import java.io.File;
import java.io.IOException; 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class Ex2_1
{

    public static String[] createTextFiles(int n, int seed, int bound){
        String baseTextFileName = "file_";
        String textToWrite = "Hello World";
        Random randomObject = new Random(seed);
        String fileNames[] = new String[n]; 
        
        for (int i = 1; i <= n; i++){
            try{
                String newFileName = baseTextFileName + Integer.toString(i) + ".txt";
                File myNewFile = new File(newFileName);
                if (myNewFile.createNewFile()) {
                    fileNames[i-1] = newFileName;
                    System.out.println("File created: " + newFileName);
                    Path filePath = Path.of(newFileName);
                    int numberOfLines = randomObject.nextInt(bound);
                    for (int j = 0; j < numberOfLines; j++){
                        if (j == 0){
                            Files.writeString(filePath, textToWrite, StandardOpenOption.APPEND);
                        } else{
                            Files.writeString(filePath, "\n"+ textToWrite, StandardOpenOption.APPEND);
                        }
                    }
                  }

            } catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        return fileNames;
    }

    public static int getNumOfLines(String[] fileNames){

        int numberOfLines = 0;

        for (int i = 0; i < fileNames.length; i++){
            List<String> lines;
            Path filePath = Paths.get(fileNames[i]);
            try{
                lines = Files.readAllLines(filePath);
                numberOfLines += lines.size();
            } catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }

        return numberOfLines;
    }

    public int getNumOfLinesThreads(String[] fileNames){
        
        return 0;
    }

    public static void main( String[] args ){
        String[] fileList = createTextFiles(3, 2, 30);
        System.out.println(getNumOfLines(fileList));
    }
}
