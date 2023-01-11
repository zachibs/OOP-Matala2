package PartB;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskCall<V> extends FutureTask<V> {
    private Callable<V> func;

    
    public FutureTaskCall(Callable<V> callable) {
        super(callable);
        this.func = callable;
    }
    
    public Callable<V> getFunc() {
        return func;
    }
    
}
