package study.hello.reactor.hello.reactor.operator.filtering;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import study.hello.reactor.hello.reactor.InitTest;

@Slf4j
public class TakeLast {

    public static void main(String[] args) throws InterruptedException {

        InitTest t = new InitTest();
        Flux
                .fromIterable(t.getList())
                .takeLast(2)//마지막 2개만
                .subscribe(data -> log.info("# onNext : {}", data));
    }
}
