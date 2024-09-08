package producer_consumer;

import java.util.ArrayDeque;
import java.util.Queue;

import static thread.util.MyLogger.log;

public class BoundedQueueV4 implements BoundedQueue {
    private final Queue<String> que = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV4(int max) {
        this.max = max;
    }
    public synchronized void put(String data) {
        while (que.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            try {
                wait();//RUNNABLE -> WAITING, 락 반납
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        que.offer(data);
        log("[put] 생산자 데이터 저장 : " + data+", notify() 호출");
        notifyAll();//대기 스레드 ,  WAIT -> BLOCKED

    }
    public synchronized String take() {
        while (que.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            try {
                wait();//락을 반납하고 대기
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String data = que.poll();
        log("[take] 소비자 데이터 획득 : " + data +", notify() 호출");
        notifyAll();
        return data;
    }
    @Override
     public String toString() {
        return que.toString();
    }
}
