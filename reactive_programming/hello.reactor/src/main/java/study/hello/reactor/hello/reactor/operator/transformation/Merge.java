package study.hello.reactor.hello.operator.transformation;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Merge {
    public static void main(String[] args) throws InterruptedException {

        Flux    //비동기 교차 -> Flux 마다 하나의 쓰레드가 아닌 emit 마다 다른 thread 할당
                .merge(
                        Flux.just(1, 2, 3, 4).delayElements(Duration.ofMillis(300L)),
                        Flux.just(5, 6, 7).delayElements(Duration.ofMillis(500L))
                )
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(2000L);
    }
}
