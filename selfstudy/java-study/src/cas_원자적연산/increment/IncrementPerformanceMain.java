package cas_원자적연산.increment;

public class IncrementPerformanceMain {

    public static final long COUNT = 100_000_000;

    public static void main(String[] args) throws InterruptedException {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());
        test(new MyAtomicInteger());
    }

    private static void test(IncrementInteger integer) throws InterruptedException {
        long startMs = System.currentTimeMillis();

        for(int i = 0 ; i < COUNT; i++){
            integer.increment();

        }

        long endMs = System.currentTimeMillis();
        System.out.println(integer.getClass().getSimpleName() + " : ms : " + (endMs - startMs));

    }
}
