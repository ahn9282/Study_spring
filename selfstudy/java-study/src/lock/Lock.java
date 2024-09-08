package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public interface Lock {

    void lock();

    void lockInterruptibly();

    boolean tryLock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
    void unLock();
    Condition newCondition();
}
