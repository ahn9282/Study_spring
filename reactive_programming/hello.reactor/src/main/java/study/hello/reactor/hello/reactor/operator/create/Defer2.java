package study.hello.reactor.hello.operator.create;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
public class Defer2 {

    public static void main(String[] args) throws InterruptedException {



        log.info("# start : {}", LocalDateTime.now());

        Mono
                .just("Hello")
                .delayElement(Duration.ofSeconds(3))
                //.switchIfEmpty(sayDefault())
                .switchIfEmpty(Mono.defer(() -> sayDefault()))
                .subscribe(data -> log.info("#on Next : {}", data));
        Thread.sleep(3000);
    }

    private static Mono<String> sayDefault(){
        log.info("# Say Hi");
        return Mono.just("Hi");
    }
}
