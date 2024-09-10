package blockingqueue;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static thread.util.MyLogger.log;

public class BoundedQueueV6_1 implements BoundedQueue {

    private BlockingQueue<String> que ;

    BoundedQueueV6_1(int max) {
        this.que = new ArrayBlockingQueue(max);
    }


    @Override
    public void put(String data) {

        try{
            que.put(data);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String take() {
        try {
            return que.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return que.toString();
    }
}
