package study.hello.reactor.hello.operator.transformation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class FlatMap2 {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .range(2, 8)
                .flatMap(dan -> Flux
                        .range(1, 9)
                        .publishOn(Schedulers.parallel())
                        .map(n -> dan + " * " + n + " = " + (dan * n)))
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(100L);

    }
}
