package blockingqueue;

public interface BoundedQueue {
    void put(String data);

    String take();
}
