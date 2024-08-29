package thread.controll;

import thread.runnable.HelloRunnable;
import thread.util.MyLogger;

import static thread.util.MyLogger.*;

public class ThreadInfoMain {
    public static void main(String[] args) {
        //main 쓰레드
        Thread mainThread = Thread.currentThread();
        log("mainThread = " + mainThread);
        log("mainThread's threadID = " + mainThread.threadId());
        log("mainThread's threadName = " + mainThread.getName());
        log("mainThread's getPriority = " + mainThread.getPriority());
        log("mainThread's threadGroup = " + mainThread.getThreadGroup());
        log("mainThread's getState = " + mainThread.getState());

        Thread myThread = new Thread(new HelloRunnable(), "myThread");
        log("mainThread = " + mainThread);
        log("mainThread's threadID = " + myThread.threadId());
        log("mainThread's threadName = " + myThread.getName());
        log("mainThread's getPriority = " + myThread.getPriority());
        log("mainThread's threadGroup = " + myThread.getThreadGroup());
        log("mainThread's getState = " + myThread.getState());//NEW
        myThread.start();
        log("mainThread's getState = " + myThread.getState());//RUNNABLE
    }
}
