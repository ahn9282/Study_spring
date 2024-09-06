package thread.volatile_study;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class VolatileCountMain {
    public static void main(String[] args) {
        MyTask work = new MyTask();
        Thread t = new Thread(work, "thread-1");
        t.start();

        sleep(1000);
        work.flag = false;
        log("flag = " + work.flag + ", count : " + work.count+" in main");
    }

    static class MyTask implements Runnable {
        long count = 0;
        boolean flag = true;
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
