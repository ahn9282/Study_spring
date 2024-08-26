package thread.problem;

import thread.util.MyLogger;

public class Problem4 {
    static int num = 0;
    public static void main(String[] args) {

        for (int i = 1; i <= 5; i++) {
            num++;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MyLogger.log("value " + num);
                }
            }, "count");
            thread.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
