package study.hello.reactor.hello.reactor.operator.filtering;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import study.hello.reactor.hello.reactor.InitTest;

@Slf4j
public class TakeUntil {

    public static void main(String[] args) throws InterruptedException {

        InitTest t = new InitTest();
        Flux
                .fromIterable(t.getList())
                .takeUntil(e -> e.length() == 8)//메서드 내 인자가 true가 나올 때까지 true인 데이터 하나까지만 emit
                .subscribe(data -> log.info("# onNext : {}", data));
    }
}
