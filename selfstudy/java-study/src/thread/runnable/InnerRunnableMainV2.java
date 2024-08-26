package thread.runnable;

import static thread.util.MyLogger.*;

public class InnerRunnableMainV2 {
    public static void main(String[] args) {
        log("main() start");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log("run");
            }
        });
        thread.start();
        Thread thread2 = new Thread(() -> log("run"));
        thread2.start();

        log("main() end");
    }

}
