package study.hello.reactor.hello.operator.transformation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class And {

    public static void main(String[] args) throws InterruptedException {

        Mono
                .just("Task 1")
                .delayElement(Duration.ofSeconds(1))
                .doOnNext(data -> log.info("# doOnNext : {}", data))
                .and(
                        Flux
                                .just("Task 2", "Task 3")
                                .delayElements(Duration.ofMillis(600))
                                .doOnNext(data -> log.info("# FluxdoOnNext : {}", data))
                )
                .subscribe(
                        data -> log.info("# onNext : {}", data),
                        error -> log.info("# onError : {}", error),
                        () -> log.info("# onComplete")
                );

        Thread.sleep(5000);
    }
}
