package blockingqueue;

import java.util.concurrent.BlockingQueue;

import static thread.util.MyLogger.log;

public class ConsumerTask2 implements Runnable {

    private BlockingQueue<String> queue;

    public ConsumerTask2(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        log("[소비 시도]    ? <= " + queue);
        String data = null;
        try {
            data = queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log("[소비 완료] " +data+" <= " + queue);

    }
}
