package executor.poolsize;

import executor.RunnableTask;

import java.util.concurrent.*;

import static executor.ExecutorUtils.*;
import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class PoolSIzeMainV1 {
    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ExecutorService es = new ThreadPoolExecutor(2, 4, 3000, TimeUnit.MILLISECONDS, workQueue);
        printState(es);

        es.execute(new RunnableTask("task1"));
        printState(es);

        es.execute(new RunnableTask("task2"));
        printState(es);

        es.execute(new RunnableTask("task3"));
        printState(es);

        es.execute(new RunnableTask("task4"));
        printState(es);

        es.execute(new RunnableTask("task5"));
        printState(es);

        es.execute(new RunnableTask("task6"));
        printState(es);



        try{
            es.execute(new RunnableTask("task7"));
        }catch(RejectedExecutionException e){
            log("task7 실행 거절 예외 발생 : " + e.getCause());
        }

        sleep(3000);
        log("== 작업 수행 완료 == ");
        printState(es);

        sleep(3000);
        log("== maximumPoolSize 대기 시간 초과 == ");
        printState(es);


        es.close();
        log("== shutDown 완료 == ");
        printState(es);
    }
}
