package thread.runnable;

import thread.util.MyLogger;
import thread.util.MyLoggerMain;

public class InnerRunnableMainV1  {
    public static void main(String[] args) {
        MyLogger.log("main() start");

        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();

        MyLogger.log("main() end");
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(" run()");

        }
    }

}
