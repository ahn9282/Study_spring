package blockingqueue;

import java.util.concurrent.BlockingQueue;

import static thread.util.MyLogger.log;

public class ProducerTask2 implements Runnable {

    private BlockingQueue<String> que;
    private String request;

    public ProducerTask2(BlockingQueue que, String request) {
        this.que = que;
        this.request = request;
    }

    @Override
    public void run() {

        log("[생산 시도] : " + request + " -> " + que);
        try {
            que.put(request);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log("[생산 완료] : " + request + " -> " + que);
    }
}

