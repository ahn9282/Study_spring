package thread.volatile_study;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class VolatileCountMainV2 {
    public static void main(String[] args) {
        MyTask work = new MyTask();
        Thread t = new Thread(work, "thread-1");
        t.start();

        sleep(1000);
        work.flag = false;
        log("flag = " + work.flag + ", count : " + work.count+" in main");
    }

    static class MyTask implements Runnable {
        volatile long count = 0;
        volatile boolean flag = true;
        //volatile로 참조하게 될경우 메인 메모리에서 값을 체크하기에 성느잉 떨어진다. 그래서 1억대에서 멈춤 안쓸경우 8억대
        @Override
        public void run() {
            while(flag){
                count++;
                if (count % 100_000_000 == 0) {
                    log("flag = " + flag + ", count : " + count+" in While()");
                }
            }
        }
    }
}
