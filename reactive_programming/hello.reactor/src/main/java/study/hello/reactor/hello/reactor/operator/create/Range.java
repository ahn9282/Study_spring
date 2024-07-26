package study.hello.reactor.hello.reactor.operator.create;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import study.hello.reactor.hello.reactor.InitTest;

@Slf4j
public class Range {

    public static void main(String[] args) {

        InitTest t = new InitTest();

        Flux
                .range(5, 10)//5에서 시작해서 10개
                .subscribe(data -> log.info("{}", data));
    }
}
