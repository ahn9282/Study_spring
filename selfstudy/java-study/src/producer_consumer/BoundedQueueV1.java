package producer_consumer;

import java.util.ArrayDeque;
import java.util.Queue;

import static thread.util.MyLogger.log;

public class BoundedQueueV1 implements BoundedQueue {
    private final Queue<String> que = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV1(int max) {
        this.max = max;
    }

    @Override
    public synchronized String take() {
        if (que.isEmpty()) {
            return null;
        }
        return que.poll();
    }

    @Override
    public synchronized void put(String data) {
        if (que.size() == max) {
            log("[put] 큐가 가득 참, 버림 : " + data);
            return;
        }
            que.offer(data);
    }
    @Override
    synchronized public String toString() {
        return que.toString();
    }
}
