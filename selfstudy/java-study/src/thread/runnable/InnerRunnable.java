package thread.runnable;

public class InnerRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " : run");

    }
}
