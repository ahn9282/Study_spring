package study.hello.reactor.hello.operator.create;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import study.hello.reactor.hello.InitTest;

import java.time.LocalDateTime;

@Slf4j
public class Defer {

    public static void main(String[] args) throws InterruptedException {

        InitTest t = new InitTest();

        log.info("# start : {}", LocalDateTime.now());
        Mono<LocalDateTime> justMono = Mono.just(LocalDateTime.now());
        Mono<LocalDateTime> deferMono = Mono.defer(() -> Mono.just(LocalDateTime.now()));

        Thread.sleep(1000);

        justMono.subscribe(data -> log.info("#onNext just1 : {}", data));
        deferMono.subscribe(data -> log.info("#onNext defer1 : {}", data));

        Thread.sleep(1000);

        justMono.subscribe(data -> log.info("#onNext just2 : {}", data));
        deferMono.subscribe(data -> log.info("#onNext defer2 : {}", data));
    }
}
