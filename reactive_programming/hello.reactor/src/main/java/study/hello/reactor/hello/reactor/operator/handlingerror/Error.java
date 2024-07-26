package study.hello.reactor.hello.operator.handlingerror;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class Error {

    public static void main(String[] args) {
        Flux
                .range(1, 5)
                .flatMap(num -> {
                    if ((num * 2) % 3 == 0) {
                        return Flux.error(new IllegalStateException("NotAllowed multiple of 3"));
                    } else { return Mono.just(num * 2);  }
                })
                .subscribe(data -> log.info("# onNext : {}", data),
                        error -> log.error("# onError : ", error));

    }
}
