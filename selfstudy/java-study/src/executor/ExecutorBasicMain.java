package executor;

import java.util.concurrent.*;

import static executor.ExecutorUtils.printState;
import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class ExecutorBasicMain {
    public static void main(String[] args) {
        // executor 구현체는 ThreadPoolExecutor를 사용하며 매개 변수에 pool size 및 시간 과 작업 수행 queue를 매개변수로 넣어 설정한다.
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        log(" == 초기 상태 == ");
        printState(es);

        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("taskD"));
        log("== 작업 수행 중 == ");
        printState(es);

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        es.close();
        log("== shutdown 완료== ");
        printState(es);
    }
}
