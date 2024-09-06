package thread.interrupt;

import thread.util.ThreadUtils;

import static thread.util.MyLogger.log;

public class ThreadStopMainV1 {
    public static void main(String[] args) {

        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();
        ThreadUtils.sleep(4000);
        log("작업 중단 지시 runFlag = false");
        task.runFlag = false;
    }

    static class MyTask implements Runnable {

        volatile boolean runFlag = true;

        @Override
        public void run() {
            try {
                while (runFlag) {

                    log("작업 중");
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            log("자원 정리");
            log("자원 종료");
        }
    }
}
