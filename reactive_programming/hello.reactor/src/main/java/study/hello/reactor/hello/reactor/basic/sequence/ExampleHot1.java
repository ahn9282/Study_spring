package study.hello.reactor.hello.reactor.basic.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
public class ExampleHot1 {
    public static void main(String[] args) throws InterruptedException {


        String[] arr = {"Singer A", "Singer B", "Singer C", "Singer D", "Singer E"};
        log.info("# begin concert : ");

        Flux<String> concertFlux = Flux
                .fromArray(arr)
                .delayElements(Duration.ofSeconds(1))
                .share();
        //share() : 원본 Flux를 멀티 캐스트 하는 새로운 Flux를 리턴한다.
        //멀티 캐스트 : 여러 Subscriber가 하나의 원본 Flux를 공유한다. 는 의미.

        concertFlux
                .subscribe(e -> log.info("# Suscriber1 is watching {}'s song", e));

        Thread.sleep(2500L);
        concertFlux
                .subscribe(e -> log.info("## Suscriber2 is watching {}'s song", e));


        Thread.sleep(2000L);
    }
}
