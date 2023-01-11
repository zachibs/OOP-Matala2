package PartB;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskCall<V> extends FutureTask<V> implements Comparable<FutureTaskCall<V>> {
    private Callable<V> func;

    
    public FutureTaskCall(Callable<V> callable) {
        super(callable);
        this.func = callable;
    }
    
    public Callable<V> getFunc() {
        return func;
    }

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
