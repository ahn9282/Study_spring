package thread.problem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static thread.util.MyLogger.log;

public class problem1 {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    public static void main(String[] args) {

        CounterThread thread = new CounterThread();
        thread.start();
    }
    static class CounterThread extends Thread{

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {

                String time = LocalTime.now().format(formatter);
                log("value : "+ i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
