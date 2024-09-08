package sync.ex;

public class Counter {

    private long count = 0;

    synchronized public void increment(){
        count += 1;
    }

    public long getCount() {
        return count;
    }
}
