package study.hello.reactor.hello.schduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ExampleOnlyPublishOn6 {
    public static void main(String[] args) throws InterruptedException {

        Flux.fromArray(new Integer[]{1, 3, 5, 7})
                .doOnNext(data -> log.info("# donOnNext fromArray: {}", data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# donOnNext filter: {}", data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# donOnNext map: {}", data))
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(500L);
    }
}
