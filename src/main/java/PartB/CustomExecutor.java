package PartB;

import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CustomExecutor is a class that extends ThreadPoolExecutor and is used to 
 * prioritize the execution of tasks by task priority. 
 *
 * The class takes in no arguments and initializes the thread pool with the
 * number of minimum and maximum cores of the system and sets the queue to
 * a PriorityBlockingQueue where the tasks are compared by their priority.
 *
 * The class implements a new submit method to submit a task along with its priority,
 * and overrides newTaskFor and afterExecute to handle the prioritization of tasks.
 * 
 * @param <V> The type of the result returned by the task's Callable
 */
public class CustomExecutor<V> extends ThreadPoolExecutor {
    /**
     * minCores holds the number of minimum cores of the system.
     */
    private static int minCores = Runtime.getRuntime().availableProcessors() / 2;
    /**
     * maxCores holds the number of maximum cores of the system.
     */
    private static int maxCores = Runtime.getRuntime().availableProcessors() - 1;
    /**
     * taskPriorityArr holds an array of integers where the index i of the array
     * represents the priority i+1, and the value at the index represents the number
     * of tasks currently in the queue with priority i+1.
     */
    int taskPriorityArr[] = new int[10];
    
    /**
     * Constructor that initializes the thread pool with the number of minimum and maximum cores 
     * of the system and sets the queue to a PriorityBlockingQueue where the tasks are compared
     * by their priority.
     */
    public CustomExecutor(){
        super(minCores , maxCores, 300 , TimeUnit.MILLISECONDS , new PriorityBlockingQueue<Runnable>(minCores,Comparator.comparing(taskToCompare ->((FutureTaskCall)taskToCompare))));
    }

    /**
     * Method that submits a task along with its priority.
     *
     * @param task the task to be executed.
     * @return a Future object representing the task
     */
    public <V> Future<V> submit(Task<V> task){
        taskPriorityArr[task.getType().getPriorityValue() - 1] += 1;
        return super.submit((Callable)task);
    }

    /**
     * Method that submits a callable and task type.
     *
     * @param call the callable representing the task to be executed
     * @param tasktype the priority of the task
     * @return a Future object representing the task
     */
    public <V> Future<V> submit(Callable call, TaskType tasktype){
        Task<V> task = Task.createTask(call, tasktype);
        return this.submit(task);
    }

    /**
     * Method that submits a callable.
     *
     * @param call the callable representing the task to be executed
     * @return a Future object representing the task
     */
    public <V> Future<V> submit(Callable<V> call){
        Task<V> task = Task.createTask(call);
        return this.submit(task);
    }

    /**
     * Method that gracefully shutdown
    /**
     * Method that gracefully shutdowns the thread pool.
     */
    public void gracefullyTerminate(){
        super.shutdownNow();
    }
    /**
     * Method that creates a new task for the provided callable, it is
     * overridden to return a FutureTaskCall object instead of a FutureTask.
     *
     * @param func the callable representing the task to be executed
     * @return a FutureTaskCall object representing the task
     */
    @Override
    protected <V> RunnableFuture<V> newTaskFor(Callable<V> func){
        return new FutureTaskCall<V>(func);
    }

    /**
     * Method that is called after the execution of a task, it is
     * overridden to decrease the count of tasks for the specific priority 
     * after the execution of the task.
     *
     * @param run the task that was executed
     * @param throww the exception thrown by the task, if any
     */
    protected void afterExecute(Runnable run, Throwable throww){
        super.afterExecute(run, throww);
        FutureTaskCall<V> futtaskcall = FutureTaskCall.class.cast(run);
        Callable<V> func = futtaskcall.getFunc();
        Task newTaskFromCallable = (Task) func;
        taskPriorityArr[(newTaskFromCallable.getType().getPriorityValue() - 1)]--;
    }
    /**
     * Method that returns the current maximum priority of the task 
     * currently in the queue, returns -1 if queue is empty.
     *
     * @return the current maximum priority of the task currently in the queue
     */
    public int getCurrentMax(){
        for(int i = 0; i < 10; i++){
            if(taskPriorityArr[i] > 0){
                return i+1;
            }
        }
        return -1;
    }

    /**
     * Method that returns a string representation of the CustomExecutor, it is
     * overridden to include information about the thread pool and the task priorities.
     *
     * @return a string representation of the CustomExecutor
     */
    public String toString(){
        return super.toString();
    }
}
