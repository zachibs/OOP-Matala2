package PartB;

import java.util.concurrent.Callable;

public class Task<V> implements Callable<V> {
    TaskType type;
    Callable<V> func;

    private Task(TaskType type, Callable<V> func){
        this.type = type;
        this.func = func;
    }

    private Task(Callable<V> func){
        this.func = func;
        this.type.setPriority(1);
    }

    @Override
    public V call() throws Exception {
        try{
            return func.call();
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public Task<V> TaskFactory(TaskType type, Callable<V> func){
        return new Task<V>(type, func);
    }
    
    public int TaskCompare(Task<V> Task2){
        if (this.type.getPriorityValue() > Task2.type.getPriorityValue()){
            return 1;
        } else if(this.type.getPriorityValue() == Task2.type.getPriorityValue()){
            return 0;
        } else{
            return -1;
        }
    }
}