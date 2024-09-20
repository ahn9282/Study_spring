package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static thread.util.MyLogger.log;

public abstract class ExecutorUtils {

    public static void printState(ExecutorService executorService) {

        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize(); //스레드 개수
            int active = poolExecutor.getActiveCount();  // 작업 중인 스레드의 개수
            int queuedTasks = poolExecutor.getQueue().size();
            long completedTask = poolExecutor.getCompletedTaskCount();
            log("[pool  :" + pool + ", active  :  " + active +
                    ", queuedTasks : " + queuedTasks + ", completedTask : " + completedTask + "] ");
        } else {
            log(executorService);
        }

    }
}
