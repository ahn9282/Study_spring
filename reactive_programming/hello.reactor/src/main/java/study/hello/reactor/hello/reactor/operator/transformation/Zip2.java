package study.hello.reactor.hello.operator.transformation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Zip2 {
    public static void main(String[] args) throws InterruptedException {
        Flux        //배열로 묶어서 emit
                .zip(
                        Flux.just(1, 2, 3).delayElements(Duration.ofMillis(300L)),
                        Flux.just(4, 5, 6).delayElements(Duration.ofMillis(500L)),
                        (n1, n2) -> n1 * n2
                )
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(2000L);
    }
}
