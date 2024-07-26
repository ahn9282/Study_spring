package study.hello.reactor.hello.reactor.operator.filtering;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import study.hello.reactor.hello.reactor.InitTest;

@Slf4j
public class FilterEx2 {
    public static void main(String[] args) {

        InitTest t = new InitTest();

        log.info("test data : {}", t.getList());
        Flux
                .fromIterable(t.getList())
                .filter(e -> e.length() <= 7)
                .subscribe(data -> log.info("# onNext : {}", data));

    }
}
