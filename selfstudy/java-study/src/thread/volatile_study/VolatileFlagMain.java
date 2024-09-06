package thread.volatile_study;


import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class VolatileFlagMain {
    public static void main(String[] args) {
        MyTask work = new MyTask();
        Thread thread = new Thread(work, "myThread");
        log("runFlag = " + work.runFlag);
        thread.start();

        sleep(1000);
        log("runFalg를 flase로 변경 시도");
        work.runFlag = false; // mian 스레드에서 false로 변환 하였고 volatile을 지정안했기에
        // runFlag = true로 main스레드 cpu코어의 캐시메모리에만 true로 저장되어 work 스레드의 캐시 메모리 내 runFlag = true로  thread는 계속 실행된다.
        log("runFlag = " + work.runFlag);
        log("메인 종료");
    }



    static class MyTask implements Runnable {
        boolean runFlag = true;
       // volatile boolean runFlag = true;
        @Override
        public void run() {

            log("task 시작");
            while(runFlag){

            }
            log("task 종료");
        }
    }
}
