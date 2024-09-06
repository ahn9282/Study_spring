package thread.exception;

public class ThreadSleepException extends RuntimeException {
    public ThreadSleepException() {
        System.out.println("ThreadSleepException 발생!!");
    }

    public ThreadSleepException(String message) {
        System.out.println("ThreadSleepException 발생!! : " + message);
    }
}
