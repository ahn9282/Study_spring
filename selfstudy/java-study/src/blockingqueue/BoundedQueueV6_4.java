package blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static thread.util.MyLogger.log;

public class BoundedQueueV6_4 implements BoundedQueue {

    private BlockingQueue<String> que ;

    BoundedQueueV6_4(int max) {
        this.que = new ArrayBlockingQueue(max);
    }


    @Override
    public void put(String data) {

          que.add(data);//java IllegalStateException : Queue Full
    }

    @Override
    public String take() {
            return que.remove(); // java.util.MoSuchElementException
    }

    @Override
    public String toString() {
        return que.toString();
    }
}
