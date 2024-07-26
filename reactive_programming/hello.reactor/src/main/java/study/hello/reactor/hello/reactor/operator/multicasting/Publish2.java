package study.hello.reactor.hello.operator.multicasting;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Publish2 {
        private static ConnectableFlux<String> publisher;
    private static int checkedAudience;
    static {
        publisher =
                Flux.just("Concert part1", "Concert part2", "Concert part3")
                        .delayElements(Duration.ofMillis(300L))
                        .publish();
    }

    public static void main(String[] args) throws InterruptedException {

        checkAudience();
        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("# audience 1 is watching {}", data));
        checkedAudience++;

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("# audience 2 is watching {}", data));
        checkedAudience++;

        checkAudience();

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("# audience 3 is watching {}", data));
        checkedAudience++;


        Thread.sleep(1000L);
    }
    public static void checkAudience(){
        if(checkedAudience >=2){
            publisher.connect();
        }
    }
}
