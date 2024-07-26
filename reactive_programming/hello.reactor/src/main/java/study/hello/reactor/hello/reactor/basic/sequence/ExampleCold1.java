package study.hello.reactor.hello.reactor.basic.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
public class ExampleCold1 {
    public static void main(String[] args) throws InterruptedException {

        Flux<String> coldFlux = Flux
                .fromIterable(Arrays.asList("KOREA", "JAPAN", "CHINESE"))
                .map(String::toLowerCase);

        coldFlux.subscribe(e -> log.info("# Subscribe1 : {}", e));
        System.out.println("====================================");

        Thread.sleep(1500L);
        coldFlux.subscribe(e -> log.info("# Subscribe2 : {}", e));
    }
}
