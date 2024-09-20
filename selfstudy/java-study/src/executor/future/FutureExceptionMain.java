package executor.future;

import java.util.concurrent.*;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class FutureExceptionMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Integer> future = es.submit(new ExCallable());

        sleep(1000);

        try {
        log("future.get() 호출 시도, future.state() : " + future.state());
        Integer result = 0;
            result = future.get();
            log("result value : " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log("ExecutionException 발생!!  : "  +  e);
            Throwable cause = e.getCause();
            log("원인 cause : " + cause);
        }
        log("future 상태 확인 future.state() : " + future.state());
    }

    static class ExCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!!");
        }
    }
}
