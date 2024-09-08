package lock;

import java.util.concurrent.locks.LockSupport;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class LockSupportMainV1 {
    public static void main(String[] args) {

        Thread thread1 = new Thread(new ParkTest(), "Thread-1");
        thread1.start();
        //잠시 대기하여 Thread-1이 park상태에 빠질 시간을 준다.
        sleep(100);
        log("Thread-1.state() : " + thread1.getState());

        Thread thread2 = new Thread(new ParkNanoTest(), "Thread-2");
        thread2.start();

        log("main -> unpark(Thread-1)");
        LockSupport.unpark(thread1);
    }

    static class ParkTest implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park();
            log("park 종료, state : " + Thread.currentThread().getState());
            log("인터럽트 상태 : " + Thread.currentThread().isInterrupted());
        }
    }
    static class ParkNanoTest implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.parkNanos(50000000);
            log("park 종료, state : " + Thread.currentThread().getState());
            log("인터럽트 상태 : " + Thread.currentThread().isInterrupted());
        }
    }
}
