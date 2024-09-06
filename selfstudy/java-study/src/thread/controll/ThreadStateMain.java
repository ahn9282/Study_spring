package thread.controll;

import thread.exception.ThreadSleepException;
import thread.util.MyLogger;

import static thread.util.MyLogger.*;

public class ThreadStateMain {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new MyRunnable());
        log("myThread.state1 : " + thread.getState());//NEW
        log(",MyThread start()");
        thread.start();
        Thread.sleep(1000);
        log("MyThread.state3 : " + thread.getState());//Timed Waiting
        Thread.sleep(4000);
        log("MyThread.state5 : " + thread.getState());
        log("end");

    }
    static class MyRunnable implements Runnable{

        @Override
        public void run() {
            try {
            log("start");
            log("<yThread.state2 : " + Thread.currentThread().getState());//RUNNABLE
                Thread.sleep(3000);
            log("thread.sleep() end");
            log("<yThread.state4 : " + Thread.currentThread().getState());

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
