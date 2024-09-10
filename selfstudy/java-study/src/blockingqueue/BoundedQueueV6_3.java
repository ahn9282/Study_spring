package blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static thread.util.MyLogger.log;

public class BoundedQueueV6_3 implements BoundedQueue {

    private BlockingQueue<String> que ;

    BoundedQueueV6_3(int max) {
        this.que = new ArrayBlockingQueue(max);
    }


    @Override
    public void put(String data) {

        boolean result = false;
        try {
            result = que.offer(data,2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log("저장 시도 결과 : " + result);
    }

    @Override
    public String take() {
        try {
            return que.poll(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return que.toString();
    }
}
