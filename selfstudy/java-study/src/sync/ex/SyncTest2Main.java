package sync.ex;

import static thread.util.MyLogger.log;

public class SyncTest2Main {
    public static void main(String[] args) throws InterruptedException {

        MyCounter counter = new MyCounter();
        MyCounter2 counter2 = new MyCounter2();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                counter.count();
            }
        };
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                counter2.count();
            }
        };
        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Thread thread2_1 = new Thread(task2, "Thread-1-2");
        Thread thread2_2 = new Thread(task2, "Thread-2-2");
        thread2_1.start();
        thread2_2.start();
        thread2_1.join();
        thread2_2.join();
    }
    static class MyCounter{

        public void count(){
            int localValue = 0;
            for(int i = 0 ; i < 1000 ; i++){
                localValue = localValue + 1;
            }
            log("result : " + localValue);
        }
    }
    static class MyCounter2{

       synchronized public void count(){
            int localValue = 0;
            for(int i = 0 ; i < 1000 ; i++){
                localValue = localValue + 1;
            }
            log("result : " + localValue);
        }
    }
}
