package executor.poolsize;

import executor.ExecutorUtils;
import executor.RunnableTask;

import java.util.concurrent.*;

import static executor.ExecutorUtils.*;
import static executor.ExecutorUtils.printState;
import static thread.util.MyLogger.log;

public class PoolSIzeMainV4 {

    private static final int TASK_SIZE = 1201;
    //private static final int TASK_SIZE = 1100;
    //private static final int TASK_SIZE = 1200;

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

        printState(es);

        long startMs = System.currentTimeMillis();
        for (int i = 1; i <= TASK_SIZE; i++) {
            String taskName = "task" + i;
            try{
                es.execute(new RunnableTask(taskName));
                printState(es);

            }catch(RejectedExecutionException e){

                log("거절 : "+  e.getCause());
            }
        }
        es.close();
        long endMs = System.currentTimeMillis();
        log("tiem : " + (endMs - startMs));


    }
}
