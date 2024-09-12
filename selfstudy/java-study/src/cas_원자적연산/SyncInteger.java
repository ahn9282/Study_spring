package cas_원자적연산;

public class SyncInteger implements IncrementInteger {

    private int value;

    @Override
    public synchronized int get() {
        return value;
    }

    @Override
    public synchronized  void increment() {
        value++;

    }
}
