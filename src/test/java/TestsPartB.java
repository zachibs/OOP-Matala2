

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

import PartB.CustomExecutor;
import PartB.Task;
import PartB.TaskType;

import java.util.concurrent.*;

public class TestsPartB<V> {
    public static final Logger logger = LoggerFactory.getLogger(TestsPartB.class);

    @Test
    public void partialTest(){
        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = (int) sumTask.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = () -> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        // var is used to infer the declared type automatically
        var priceTask = customExecutor.submit(() -> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);

        var reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = (Double) priceTask.get();
            reversed = (String) reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }   
        assertEquals(reversed.toString(), "ZYXWVUTSRQPONMLKJIHGFEDCBA");
        assertEquals(totalPrice, 1104.0808032);
        assertEquals(customExecutor.getCurrentMax(), -1);
        logger.info(() -> "Reversed String = " + reversed);
        logger.info(() -> String.valueOf("Total Price = " + totalPrice));
        logger.info(() -> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }

    @Test
    public void secondTest(){
        CustomExecutor customExecutor = new CustomExecutor();

        Callable<String> secondTask = () ->
        {
            StringBuilder sb = new StringBuilder("HOWAREYOUDOING");
            sleep(2500);
            return sb.reverse().toString();
        };

        Callable<String> thirdTask = () ->
        {
            StringBuilder sb = new StringBuilder("HOWAREYOUDOING");
            sleep(2500);
            return sb.reverse().toString();
        };

        Callable<String> fourthTask = () ->
        {
            StringBuilder sb = new StringBuilder("HOWAREYOUDOING");
            sleep(2500);
            return sb.reverse().toString();
        };

        Callable<String> fifthTask = () ->
        {
            StringBuilder sb = new StringBuilder("HOWAREYOUDOING");
            sleep(2500);
            return sb.reverse().toString();
        };

        Callable<Integer> calcTask = () -> {
            return (int) (Math.pow(8,4) * 3);
        };

        Future<String> reverseTask = customExecutor.submit(secondTask, TaskType.OTHER);
        Future<String> reverseTask2 = customExecutor.submit(thirdTask, TaskType.OTHER);
        Future<String> reverseTask3 = customExecutor.submit(fourthTask, TaskType.IO);
        Future<String> reverseTask4 = customExecutor.submit(fifthTask, TaskType.IO);
        Future<Integer> calculationTask = customExecutor.submit(calcTask, TaskType.COMPUTATIONAL);

        String reversed;
        String reversed2;
        String reversed3;
        String reversed4;
        int calc;

        try
        {
            reversed = reverseTask.get();
            reversed2 = reverseTask2.get();
            reversed3 = reverseTask3.get();
            reversed4 = reverseTask4.get();
            calc = calculationTask.get();
        }
        catch (ExecutionException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        assertEquals(calc, 12288);
        assertEquals(reversed.toString(), reversed2.toString());
        assertEquals(reversed.toString(), reversed2.toString());
        assertEquals(reversed2.toString(), reversed3.toString());
        assertEquals(reversed3.toString(), reversed4.toString());
        logger.info(() -> "String after reversing = " + reversed);
        logger.info(() -> "Calculation = " + calc);
        customExecutor.gracefullyTerminate();
    }
}
