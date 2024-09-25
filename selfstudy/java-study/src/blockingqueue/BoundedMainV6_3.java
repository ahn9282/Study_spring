package blockingqueue;

import java.util.ArrayList;
import java.util.List;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class BoundedMainV6_3 {
    public static void main(String[] args) {

        BoundedQueue que = new BoundedQueueV6_3(2);

        producerFirst(que);
       //  consumerFirst(que);
    }

    private static void producerFirst(BoundedQueue queue) {
        log("== [생산자 먼저 실행]  시자그 " + queue.getClass().getSimpleName() + " ==");
        ArrayList<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllState(queue, threads);
        startConsumer(queue, threads);
        printAllState(queue, threads);
        log("== [생산자 먼저 실행] 종료 " + queue.getClass().getSimpleName() + " ==");
    }

    private static void consumerFirst(BoundedQueue queue) {
        log("== [소비자 먼저 실행]  시자그 " + queue.getClass().getSimpleName() + " ==");
        ArrayList<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        log("== [소비자 먼저 실행] 종료 " + queue.getClass().getSimpleName() + " ==");
    }

    private static void startProducer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("생산자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data" + i),
                    "producer" + i);
            threads.add(producer);
            producer.start();
            sleep(100);
        }
    }

    private static void startConsumer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("소비자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer" +
                    i);
            threads.add(consumer);
            consumer.start();
            sleep(100);
        }
    }

    private static void printAllState(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("현재 상태 출력, 큐 데이터 " + queue);
        for (Thread thread : threads) {
            log(thread.getName() + " : " + thread.getState());
        }
    }
}