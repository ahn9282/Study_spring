package cas_원자적연산;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class CasMainV3 {
    private static int THREAD_COUNT = 2;
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value = " + atomicInteger.get());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                incrementAndGet(atomicInteger);
            }
        };
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        }

        int result = atomicInteger.get();
        System.out.println(atomicInteger.getClass().getSimpleName() + ", result : "+ result + ", value : " + atomicInteger.get() ) ;
    }
    private static int incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;
        do {
            getValue = atomicInteger.get();
            sleep(100);
            log("getValue : " + getValue);
            result = atomicInteger.compareAndSet(getValue, getValue + 1);
            log("result : " + result);

        } while (!result);
        return getValue + 1;
    }

}
