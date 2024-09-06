package thread.interrupt;

import thread.util.ThreadUtils;

import static thread.util.MyLogger.log;

public class ThreadStopMainV4 {
    public static void main(String[] args) {

        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();
        ThreadUtils.sleep(300);
        log("작업 중단 지시 thread.interrupt()");
        thread.interrupt();
        log("work 스레드 인터럽트 상태 1 : " + thread.isInterrupted());
        log("work 스레드 상태 :" + thread.getState());
    }

    static class MyTask implements Runnable {

        volatile boolean runFlag = true;

        @Override
        public void run() {
            //interrupted 메서드는 인터럽트의 경우 true를 반환하고 그 후 interrupt상태를 false로 바꾼다.
            while (!Thread.interrupted()) {
                log("작업 중");
            }
            log("work 스레드 인터럽트 상태2 :  " + Thread.currentThread().isInterrupted());

            //interrupted 메소드로 인해 interrupt가 false로 바뀌어 InterruptedException 발생 안함
            try {
                log("자원 정리");
                Thread.sleep(1000);
                log("자원 종료");
            } catch (InterruptedException e) {
                log("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
                log("work 스레드 인터럽트 상태 3 : " + Thread.currentThread().isInterrupted());
            }
        }
    }
}
