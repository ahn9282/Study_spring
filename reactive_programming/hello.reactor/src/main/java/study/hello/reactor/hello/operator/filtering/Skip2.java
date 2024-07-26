package study.hello.reactor.hello.operator.filtering;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Skip2 {
    public static void main(String[] args) throws InterruptedException {

        Flux
                .interval(Duration.ofMillis(300))
                .skip(Duration.ofSeconds(1))//0에서 1초까지 진행되느느 emit skip
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(2000L);
    }
}
