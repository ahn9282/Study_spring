package study.hello.reactor.hello.operator.filtering;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Take2 {

    public static void main(String[] args) throws InterruptedException {

        Flux
                .interval(Duration.ofSeconds(1))
                .take(Duration.ofMillis(4000))//1초마다 emit 그리고 4까지만 적용
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(6000L);
    }
}
