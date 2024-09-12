package cas_원자적연산.increment;

import java.util.concurrent.atomic.AtomicInteger;

public class MyAtomicInteger implements IncrementInteger {


    AtomicInteger atomicInteger =  new AtomicInteger(0);

    @Override
    public  int get() {
        return atomicInteger.get();
    }

    @Override
    public   void increment() {
        atomicInteger.incrementAndGet();

    }
}
