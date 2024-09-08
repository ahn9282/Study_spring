package producer_consumer;

import java.util.ArrayDeque;
import java.util.Queue;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class BoundedQueueV2 implements BoundedQueue {
    private final Queue<String> que = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV2(int max) {
        this.max = max;
    }
    public synchronized void put(String data) {
        while (que.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            sleep(1000);
        }
        que.offer(data);
    }
    public synchronized String take() {
        while (que.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            sleep(1000);
        }
        return que.poll();
    }
    @Override
     public String toString() {
        return que.toString();
    }
}
