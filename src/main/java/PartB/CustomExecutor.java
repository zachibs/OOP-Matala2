package PartB;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomExecutor<V> extends ThreadPoolExecutor {

    private static int minCores = Runtime.getRuntime().availableProcessors() / 2;
    private static int maxCores = Runtime.getRuntime().availableProcessors() - 1;

    private static PriorityBlockingQueue taskQueue = new PriorityBlockingQueue<>(minCores);
    int taskPriorityArr[] = new int[15];

    public CustomExecutor(){
        super(minCores, maxCores, 300, TimeUnit.MILLISECONDS, taskQueue);
    }

    public Future<V> submit(Task<V> task){
        taskPriorityArr[task.getType().getPriorityValue() - 1] += 1;
        Future<V> future = super.submit((Callable <V>)task);
        return future;
    }

    public Future<V> submit(Callable<V> call, TaskType tasktype){
    Task<V> task = Task.createTask(call, tasktype);
    Future<V> future = this.submit(task);
    return future;
    }
    
    public Future<V> submit(Callable call){
        Task<V> task = Task.createTask(call);
        Future<V> future = this.submit(task);
        return future;
        }
    
    // public V get() throws InterruptedException, ExecutionException{
    //     return this.get();
    // }
    
    public void gracefullyTerminate(){
        super.shutdownNow();
    }

    @Override
    protected <V> RunnableFuture<V> newTaskFor(Callable<V> func){
        return new FutureTaskCall<V>(func);
    }

    protected void afterExecute(Runnable run, Throwable throww){
        super.afterExecute(run, throww);
        FutureTaskCall<V> futtaskcall = FutureTaskCall.class.cast(run);
        Callable<V> func = futtaskcall.getFunc();
        Task newTaskFromCallable = (Task) func;
        taskPriorityArr[(newTaskFromCallable.getType().getPriorityValue() - 1)]--;
    }

    public int getCurrentMax(){
        for(int i = 0; i < 15; i++){
            if(taskPriorityArr[i] > 0){
                return i+1;
            }
        }
        return -1;
    }
}
