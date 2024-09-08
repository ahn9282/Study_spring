package producer_consumer;

public interface BoundedQueue {
    void put(String data);

    String take();
}
