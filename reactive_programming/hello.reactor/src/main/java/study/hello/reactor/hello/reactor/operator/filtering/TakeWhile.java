package study.hello.reactor.hello.reactor.operator.filtering;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import study.hello.reactor.hello.reactor.InitTest;

@Slf4j
public class TakeWhile {

    public static void main(String[] args) throws InterruptedException {

        InitTest t = new InitTest();
        Flux
                .fromIterable(t.getList())
                .takeWhile(e -> e.length() == 7)//true일 동안에만 그러나 시작부터 false일 경우 아예 X
                .subscribe(data -> log.info("# onNext : {}", data));
    }
}
