package cas_원자적연산;

public class VolatileInteger implements IncrementInteger {

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
