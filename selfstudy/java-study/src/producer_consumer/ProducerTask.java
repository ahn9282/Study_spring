package producer_consumer;

import static thread.util.MyLogger.log;

public class ProducerTask implements Runnable {

    private BoundedQueue que;
    private String request;

    public ProducerTask(BoundedQueue que, String request) {
        this.que = que;
        this.request = request;
    }

    @Override
    public void run() {

        log("[생산 시도] : " + request + " -> " + que);
        que.put(request);
        log("[생산 완료] : " + request + " -> " + que);
    }
}
