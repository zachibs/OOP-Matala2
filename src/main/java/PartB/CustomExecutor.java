package PartB;

import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomExecutor<V> extends ThreadPoolExecutor {

    private static int minCores = Runtime.getRuntime().availableProcessors() / 2;
    private static int maxCores = Runtime.getRuntime().availableProcessors() - 1;

    int taskPriorityArr[] = new int[10];
    
    public CustomExecutor(){
        super(minCores , maxCores, 300 , TimeUnit.MILLISECONDS , new PriorityBlockingQueue<Runnable>(minCores,Comparator.comparing(taskToCompare ->((FutureTaskCall)taskToCompare))));
    }

    public <V> Future<V> submit(Task<V> task){
        taskPriorityArr[task.getType().getPriorityValue() - 1] += 1;
        return super.submit((Callable)task);
    }

    public <V> Future<V> submit(Callable call, TaskType tasktype){
    Task<V> task = Task.createTask(call, tasktype);
    return this.submit(task);
    }
    
    public <V> Future<V> submit(Callable<V> call){
        Task<V> task = Task.createTask(call);
        return this.submit(task);
        }
    
    
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
        for(int i = 0; i < 10; i++){
            if(taskPriorityArr[i] > 0){
                return i+1;
            }
        }
        return -1;
    }

    public String toString(){
        return super.toString();
    }
}
