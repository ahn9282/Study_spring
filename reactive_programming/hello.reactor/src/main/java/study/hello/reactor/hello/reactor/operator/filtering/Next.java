package study.hello.reactor.hello.reactor.operator.filtering;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import study.hello.reactor.hello.reactor.InitTest;

@Slf4j
public class Next {
    public static void main(String[] args) {
        InitTest t = new InitTest();
        Flux
                .fromIterable(t.getList())
                .next()// Upstream에서 emit되는 첫 번째 데이터만
                .subscribe(data -> log.info("# onNext : {}", data));
    }
}
