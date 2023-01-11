

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import PartA.Ex2_1;


public class TestsPartA {

    static Ex2_1 testObject = new Ex2_1();
    static String[] fileNames;

    @BeforeAll
    public static void getFileNames(){
        fileNames = testObject.createTextFiles(100, 2, 1000);
    }

    @Test
    public void getNumOfLinesTest(){
        double firstTimeMili = System.currentTimeMillis();
        System.out.print("Regular counting: ");
        System.out.print(testObject.getNumOfLines(this.fileNames));
        System.out.print("\nTime took: " + testObject.differenceTimeMili(firstTimeMili));
    }

    @Test
    public void getNumOfLinesThreadsTest(){
        double firstTimeMili = System.currentTimeMillis();
        System.out.print("\nThread counting: ");
        System.out.print(testObject.getNumOfLinesThreads(this.fileNames)); 
        System.out.print("\nTime took: " + testObject.differenceTimeMili(firstTimeMili));
    }

    @Test
    public void getNumOfLinesThreadPoolTest(){
        double firstTimeMili = System.currentTimeMillis();
        System.out.print("\nThreadPool counting: ");
        System.out.print(testObject.getNumOfLinesThreadPool(this.fileNames));      
        System.out.print("\nTime took: " + testObject.differenceTimeMili(firstTimeMili));
    }

}
