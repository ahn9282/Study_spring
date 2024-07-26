package study.hello.reactor.hello.operator.transformation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class And2 {

    public static void main(String[] args) throws InterruptedException {

        Mono
                .just("Application Server was restarted successfully.")
                .delayElement(Duration.ofSeconds(2))
                .doOnNext(log::info)
                .and(
                        Mono
                                .just("DB Server was restarted successfully.")
                                .delayElement(Duration.ofSeconds(4))
                                .doOnNext(log::info)
                )
                .subscribe(
                        data -> log.info("# onNext : {}", data),
                        error -> log.info("# onError : {}", error),
                        () -> log.info("# sent an email to Administrator : " +
                                "All Servers are restarted successfully")
                );

        Thread.sleep(5000);
    }
}
