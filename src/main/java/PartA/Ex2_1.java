package PartA;

import java.io.File;
import java.io.IOException; 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Ex2_1 class provides methods for creating text files and reading the number of lines from a given file.
 * It uses multi-threading to read the number of lines from a given file.
 *
 */
public class Ex2_1 {

    /**
     * Creates text files with a given number of lines.
     *
     * @param n - number of files to create
     * @param seed - to generate a known set of random numbers
     * @param bound - upper limit for lines to generate
     * @return the names of the created files
     */
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
                    // System.out.println("File created: " + newFileName);
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

    /**
     * Reads the number of lines from a list of files.
     *
     * @param fileNames - a list of file names 
     * @return the total number of lines in the files
     */
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

    /**
     * Reads the number of lines from a list of files using multiple threads.
     *
     * @param fileNames - a list of file names 
     * @return the total number of lines in the files
     */
    public int getNumOfLinesThreads(String[] fileNames){
        List<NumReaderThread> threadList = new ArrayList();
        int sumOfLines = 0;
        
        for(int i = 0; i < fileNames.length; i++){
            String name = fileNames[i];
            NumReaderThread myNewThread = new NumReaderThread(name);
            threadList.add(myNewThread);
        }
        
        for(int i = 0; i < fileNames.length; i++){
            threadList.get(i).start();
        }

        for(int i = 0; i < fileNames.length; i++){
            try{
                threadList.get(i).join();
            } catch (InterruptedException e){
                System.out.println(e);
            }
            sumOfLines += threadList.get(i).getNumOfLines();
        }
        return sumOfLines;
    }
    
    /**
     * Reads the number of lines from a list of files using a thread pool.
     *
     * @param fileNames - a list of file names 
     * @return the total number of lines in the files
     */
    public int getNumOfLinesThreadPool(String[] fileNames){
        int sumOfLines = 0;

        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);
        Future<Integer> futureList[] = new Future[fileNames.length];

        for(int i = 0; i < fileNames.length; i++){
            String name = fileNames[i];
            NumReaderCallable myNewThread = new NumReaderCallable(name);
            Future<Integer> intResult = threadPool.submit(myNewThread);
            futureList[i] = intResult;
        }

        for(int i = 0; i < fileNames.length; i++){
            try{
                sumOfLines += futureList[i].get();
            } catch (InterruptedException e){
                System.out.println(e);
            } catch(ExecutionException e){
                System.out.println(e);
            }
        }

        threadPool.shutdown();
        
        return sumOfLines;
    }

    
    /** 
     * @param firstTimeMili
     * @return 
     */
    public double differenceTimeMili(double firstTimeMili){
        double lastTimeMili = System.currentTimeMillis();

        return (lastTimeMili - firstTimeMili) / 1000;
    }

}
