package producer_consumer;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static thread.util.MyLogger.log;

public class BoundedQueueV6 implements BoundedQueue {

    //2개의 condition 객체를 두어 각 메서드마다 다른 condition 객체에 스레드를 깨워(비즈니스 로직에 맞춰서 함)
    private final Lock lock = new ReentrantLock();
    private final Condition consumerCondition = lock.newCondition();
    private final Condition producerCondition = lock.newCondition();

    private final Queue<String> que = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV6(int max) {
        this.max = max;
    }

    public  void put(String data) {
        lock.lock();
        try{

        while (que.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            try {
                producerCondition.await();
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        que.offer(data);
        log("[put] 생산자 데이터 저장 : " + data + ", producerCondition.signal() 호출");
            consumerCondition.signal();
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
                consumerCondition.await();
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String data = que.poll();
        log("[take] 소비자 데이터 획득 : " + data + ", producerCondition.signal() 호출");
            producerCondition.signal();
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
