package study.hello.reactor.hello.operator.multicasting;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Publish {

    public static void main(String[] args) throws InterruptedException {

        ConnectableFlux<Integer> flux = Flux
                .range(1, 5)
                .delayElements(Duration.ofMillis(300L))
                .publish();

        Thread.sleep(500L);
        flux.subscribe(data -> log.info("# subscribe 1 :{}", data));

        Thread.sleep(200L);
        flux.subscribe(data -> log.info("# subscribe 2 :{}", data));
        flux.connect();
        Thread.sleep(1000L);
        flux.subscribe(data -> log.info("# subscribe 3 :{}", data));

        Thread.sleep(2000L);
    }
}
