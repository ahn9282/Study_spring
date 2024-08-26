package thread.problem;

import thread.util.MyLogger;

public class Problem2 {
    public static void main(String[] args) {
        Thread thread = new Thread(new CountRunnable(), "count");
        thread.start();
    }
}
class CountRunnable implements Runnable {
    @Override
    public void run() {
        for(int i = 1 ; i <=5; i++){
            MyLogger.log("value : " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
