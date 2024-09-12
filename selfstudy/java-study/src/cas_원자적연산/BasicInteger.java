package cas_원자적연산;

public class BasicInteger implements IncrementInteger {

    private int value;

    @Override
    public int get() {
        return value;
    }

    @Override
    public void increment() {
        value++;

    }
}
