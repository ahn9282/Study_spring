package study.hello.reactor.hello.reactor.operator.filtering;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import study.hello.reactor.hello.reactor.InitTest;

@Slf4j
public class Skip3 {
    public static void main(String[] args) throws InterruptedException {

        InitTest t = new InitTest();

        Flux
                .fromIterable(t.getList())
                .filter(e -> e.length() > 7)
                .skip(2)// 맨 앞 2개 skip
                .subscribe(data -> log.info("# member's name : {}", data));
    }
}
