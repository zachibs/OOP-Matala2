package PartB;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
/**
 * Task is a class that extends FutureTask and implements Callable and Comparable.
 *
 * The class is used to represent a task that can be executed by the thread pool. Each task has 
 * a priority, represented by a TaskType, and a callable function, represented by a Callable object. 
 *
 * The class implements a new submit method to submit a task along with its priority,
 * and overrides the newTaskFor to handle the prioritization of tasks.
 *
 * @param <V> The type of the result returned by the task's Callable
 */
public class Task<V> extends FutureTask<V> implements Callable<V>, Comparable<Task<V>> {
    /**
     * type holds the priority of the task, represented by a TaskType object.
     */
    private TaskType type;
    /**
     * func holds the callable function, represented by a Callable object.
     */
    private Callable<V> func;

    /**
     * Private constructor that initializes the task with its priority and callable function.
     *
     * @param type The priority of the task
     * @param func The callable function representing the task
     */
    private Task(TaskType type, Callable<V> func){
        super(func);
        this.type = type;
        this.func = func;
    }

    /**
     * Overrides the call method of the Callable interface to execute the task's callable function
     * and catch any exceptions thrown.
     *
     * @return The result returned by the task's callable function
     * @throws Exception if the task's callable function throws an exception
     */
    @Override
    public V call() throws Exception {
        try{
            return this.func.call();
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    /**
     * Returns the priority of the task.
     *
     * @return The priority of the task
     */
    public TaskType getType() {
        return this.type.getType();
    }

    /**
     * Returns the callable function of the task.
     *
     * @return The callable function of the task
     */
    public Callable<V> getFunc() {
        return this.func;
    }

    /**
     * Creates a new task object with the given callable function and priority.
     *
     * @param func the callable function representing the task
     * @param type the priority of the task
     * @return a new task object
     */
    public static Task createTask(Callable func, TaskType type){
        return new Task(type, func);
    }

    /**
     * Creates a new task object with the given callable function and default priority.
     *
     * @param func the callable function representing the task
     * @return a new task object
     */
    public static Task createTask(Callable func){
        return new Task(TaskType.OTHER, func);
    }

    /**
     * Compares the priority of this task with the given task.
     *
     * @param Task2 The task to
    /**
     * Compares the priority of this task with the given task.
     *
     * @param Task2 The task to be compared
     * @return 1 if this task has higher priority, 0 if both tasks have the same priority, -1 if this task has lower priority
     */
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
