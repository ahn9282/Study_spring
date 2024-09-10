package blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static thread.util.MyLogger.log;

public class BoundedQueueV6_2 implements BoundedQueue {

    private BlockingQueue<String> que ;

    BoundedQueueV6_2(int max) {
        this.que = new ArrayBlockingQueue(max);
    }


    @Override
    public void put(String data) {

        boolean result = que.offer(data);
        log("저장 시도 결과 : " + result);
    }

    @Override
    public String take() {
        return que.poll();
    }

    @Override
    public String toString() {
        return que.toString();
    }
}
