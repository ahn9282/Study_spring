package thread.controll;

import thread.exception.ThreadSleepException;
import thread.util.ThreadUtils;

import static thread.util.ThreadUtils.*;

public class CheckedExceptionMain {

    public static void main(String[] args) throws Exception {
        throw new Exception();
    }

    static class CheckedRunnable implements  Runnable{

        @Override
        public void run() {
            System.out.println("Runnable.Run()!!!");
            sleep(1000);
        }
    }
    public static void sleepThread(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new ThreadSleepException();
        }
    }
}
