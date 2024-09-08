package sync.ex;

import static thread.util.MyLogger.log;

public class SyncTest3Main {
    public static void main(String[] args) throws InterruptedException {
        Immutable immutable =new Immutable(400);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                log("value  : " + immutable.getValue());
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }
}
