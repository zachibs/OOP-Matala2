package PartB;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * FutureTaskCall is a class that extends FutureTask and implements Comparable.
 *
 * The class is used to represent a task that can be executed by the thread pool, and it provides
 * a way to compare two tasks based on their priorities. Each task has a priority, represented by a TaskType, 
 * and a callable function, represented by a Callable object.
 *
 * The class overrides the newTaskFor method to handle the prioritization of tasks based on their priority.
 *
 * @param <V> The type of the result returned by the task's Callable
 */
public class FutureTaskCall<V> extends FutureTask<V> implements Comparable<FutureTaskCall<V>> {
    /**
     * func holds the callable function, represented by a Callable object.
     */
    private Callable<V> func;

    /**
     * Constructor that initializes the future task with the provided callable function.
     *
     * @param callable The callable function representing the task
     */
    public FutureTaskCall(Callable<V> callable) {
        super(callable);
        this.func = callable;
    }

    /**
     * Returns the callable function
    /**
     * Returns the callable function of the future task.
     *
     * @return The callable function of the future task
     */
    public Callable<V> getFunc() {
        return func;
    }

    /**
     * Compares the priority of this future task with the given future task.
     *
     * @param o The future task to be compared
     * @return 1 if this future task has higher priority, 0 if both future tasks have the same priority, -1 if this future task has lower priority
     */
    @Override
    public int compareTo(FutureTaskCall<V> o) {
        if (((Task<V>)this.func).getType().getPriorityValue() > (((Task.createTask(o.getFunc())).getType().getPriorityValue()))){
            return 1;
        } else if(((Task<V>)this.func).getType().getPriorityValue() == (((Task.createTask(o.getFunc())).getType().getPriorityValue()))){
            return 0;
        } else{
            return -1;
        }
    }
}
