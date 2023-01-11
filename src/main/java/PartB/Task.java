package PartB;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Task<V> extends FutureTask<V> implements Callable<V>, Comparable<Task<V>> {
    private TaskType type;
    private Callable<V> func;

    
    
    private Task(TaskType type, Callable<V> func){
        super(func);
        this.type = type;
        this.func = func;
    }


    @Override
    public V call() throws Exception {
        try{
            return this.func.call();
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    public TaskType getType() {
        return this.type.getType();
    }

    public Callable<V> getFunc() {
        return this.func;
    }


    public static Task createTask(Callable func, TaskType type){
        return new Task(type, func);
    }

    public static Task createTask(Callable func){
        return new Task(TaskType.OTHER, func);
    }
    
    @Override
    public int compareTo(Task<V> Task2) {
        if (this.type.getPriorityValue() > Task2.type.getPriorityValue()){
            return 1;
        } else if(this.type.getPriorityValue() == Task2.type.getPriorityValue()){
            return 0;
        } else{
            return -1;
        }
    }

}