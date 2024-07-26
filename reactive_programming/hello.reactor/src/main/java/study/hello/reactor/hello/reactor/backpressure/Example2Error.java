package study.hello.reactor.hello.reactor.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class Example2Error {
    public static void main(String[] args) throws InterruptedException {

        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureError()
                .doOnNext(e -> log.info("# doOnNext : {}", e))
                .publishOn(Schedulers.parallel())
                .subscribe(e -> {
                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException ex) {
                            }

                            log.info("# onNext : {}", e);
                        },
                        error -> log.info("# onError"));
        Thread.sleep(2000L);

    }
}
