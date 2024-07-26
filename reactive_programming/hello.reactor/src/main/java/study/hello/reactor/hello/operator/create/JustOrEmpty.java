package study.hello.reactor.hello.operator.create;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class JustOrEmpty {
    public static void main(String[] args) {
        Mono
                .justOrEmpty(null)
                .subscribe(data -> {},
                        error -> { },
                        () -> log.info("# onComplete"));
    }
}
