package study.hello.reactor.hello.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class Example3Drop {
    public static void main(String[] args) throws InterruptedException {

        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureDrop(e -> log.info("# dropped : {}", e))
                .publishOn(Schedulers.parallel())
                .subscribe(e -> {
                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException exception) {
                            }
                            log.info("# onNext : {}", e);
                        },
                        error -> log.error("# onError", error));

        Thread.sleep(2000L);
    }
}
