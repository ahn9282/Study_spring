package thread.util;

import thread.exception.ThreadSleepException;

import static thread.util.MyLogger.*;

public abstract class ThreadUtils {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log("인터럽트 발생!!, " + e.getMessage());
            throw new ThreadSleepException(e.getMessage());
        }

    }
}
