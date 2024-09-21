package executor.poolsize;

import executor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static executor.ExecutorUtils.printState;
import static thread.util.MyLogger.log;

public class PoolSIzeMainV3 {
    public static void main(String[] args) {

        //이와 같다 Executors.newFixedThreadPool(스레드 개수 고정)
        // ExecutorService es = new ThreadPoolExecutor(2, nThreads, 0L, TimeUnit.MILLISECONDS, workQueue);
        //ExecutorService es = Executors.newFixedThreadPool(2);
        ExecutorService es = Executors.newCachedThreadPool();

        log("pool 생성");
        printState(es);

        for (int i = 1; i <= 12; i++) {
            String taskName = "task " + i;
            es.execute(new RunnableTask(taskName));
            printState(es);
        }
        es.close();
        log("== shutdown == ");
    }
}
