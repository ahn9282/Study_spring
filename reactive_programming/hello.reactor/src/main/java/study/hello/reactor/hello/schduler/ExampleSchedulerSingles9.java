package study.hello.reactor.hello.schduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ExampleSchedulerSingles9 {
    public static void main(String[] args) throws InterruptedException {
        doTask("task1")
                .subscribe(data -> log.info("# onNext : {}", data));
        doTask("task2")
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(200L);
        System.out.println("==========new Single===========================");

        doTaskNewSingle("task3")
                .subscribe(data -> log.info("# onNext : {}", data));
        doTaskNewSingle("task4")
                .subscribe(data -> log.info("# onNext : {}", data));
    }

    private static Flux<Integer> doTask(String taskName) {
        return Flux.fromArray(new Integer[]{1, 3, 5, 7})
                .publishOn(Schedulers.single())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# {} doOnNext filter : {}", taskName, data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# {} doOnNext map : {}", taskName, data));
    }
    private static Flux<Integer> doTaskNewSingle(String taskName) {
        return Flux.fromArray(new Integer[]{1, 3, 5, 7})
                .publishOn(Schedulers.newSingle("new-single", true))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# {} doOnNext filter : {}", taskName, data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# {} doOnNext map : {}", taskName, data));
    }

}
