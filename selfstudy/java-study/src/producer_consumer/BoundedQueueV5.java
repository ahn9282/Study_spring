package producer_consumer;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static thread.util.MyLogger.log;

public class BoundedQueueV5 implements BoundedQueue {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private final Queue<String> que = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV5(int max) {
        this.max = max;
    }

    public  void put(String data) {
        lock.lock();
        try{

        while (que.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            try {
                condition.await();
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        que.offer(data);
        log("[put] 생산자 데이터 저장 : " + data + ", notify() 호출");
        condition.signal();
        }finally{
            lock.unlock();
        }

    }

    public synchronized String take() {
        lock.lock();
        try{

        while (que.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            try {
                condition.await();
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String data = que.poll();
        log("[take] 소비자 데이터 획득 : " + data + ", notify() 호출");
        condition.signal();
        return data;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return que.toString();
    }
}
