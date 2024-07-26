package study.hello.reactor.hello.sink;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.*;

@Slf4j
public class ExampleSinkOne3 {

    public static void main(String[] args) throws InterruptedException {

        Sinks.One<String> sinkOne = Sinks.one();
        Mono<String> mono = sinkOne.asMono();

        sinkOne.emitValue("Hello Reactor", FAIL_FAST);
        sinkOne.emitValue("Hi Reactor", FAIL_FAST);

        mono.subscribe(data -> log.info("# Subscribe 1 {}", data));
        mono.subscribe(data -> log.info("# Subscribe 2 {}", data));
    }
}

