package study.hello.reactor.hello.operator.filtering;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Skip {
    public static void main(String[] args) throws InterruptedException {

        Flux
                .interval(Duration.ofSeconds(1))
                .skip(2)//0 ~ 1 까지의 두 번의 emint을 skip
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(5000L);
    }
}
