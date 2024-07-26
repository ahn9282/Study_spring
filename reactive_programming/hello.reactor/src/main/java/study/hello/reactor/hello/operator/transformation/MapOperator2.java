package study.hello.reactor.hello.operator.transformation;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import study.hello.reactor.hello.InitTest;

@Slf4j
public class MapOperator2 {
    public static void main(String[] args) {

        InitTest t = new InitTest();
        Flux
                .fromIterable(t.getList())
                .filter(e -> e.length() >= 8)
                .doOnNext(data -> log.info("# doOnNext : {}", data))
                .map(e -> e.replace("member", "user"))
                .subscribe(data -> log.info("# onNext : {}", data));

    }
}
