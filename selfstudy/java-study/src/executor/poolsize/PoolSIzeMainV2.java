package executor.poolsize;

import executor.ExecutorUtils;
import executor.RunnableTask;

import java.util.concurrent.*;

import static executor.ExecutorUtils.*;
import static executor.ExecutorUtils.printState;
import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class PoolSIzeMainV2 {
    public static void main(String[] args) {

        //이와 같다 Executors.newFixedThreadPool(스레드 개수 고정)
        // ExecutorService es = new ThreadPoolExecutor(2, nThreads, 0L, TimeUnit.MILLISECONDS, workQueue);
        ExecutorService es = Executors.newFixedThreadPool(2);


        log("pool 생성");
        printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task " + i;
            es.execute(new RunnableTask(taskName));
            printState(es);
        }
        es.close();
        log("== shutdown == ");
    }
}
