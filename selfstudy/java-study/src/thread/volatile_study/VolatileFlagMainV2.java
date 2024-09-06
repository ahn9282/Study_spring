package thread.volatile_study;


import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class VolatileFlagMainV2 {
    public static void main(String[] args) {
        MyTask work = new MyTask();
        Thread thread = new Thread(work, "myThread");
        log("runFlag = " + work.runFlag);
        thread.start();

        sleep(1000);
        log("runFalg를 flase로 변경 시도");
        work.runFlag = false;
        log("runFlag = " + work.runFlag);
        log("메인 종료");
    }



    static class MyTask implements Runnable {
       // boolean runFlag = true;
        volatile boolean runFlag = true;
        //volatile의 경우 main 메모리의 변수만을 참조하ㅣ여 성능 상으로는 떨어질 수 있어 필요한 경우엡만 사용하며 이 경우 지정 변수의 스레드 공동으로 사용
        @Override
        public void run() {

            log("task 시작");
            while(runFlag){

            }
            log("task 종료");
        }
    }
}
