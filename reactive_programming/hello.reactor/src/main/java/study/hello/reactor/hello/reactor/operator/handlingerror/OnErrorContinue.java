package study.hello.reactor.hello.operator.handlingerror;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OnErrorContinue {

    public static void main(String[] args) {

        Flux
                .just(1, 2, 4, 0, 6, 12)
                .map(num -> 12 / num)
                .onErrorContinue((error, num) ->
                        log.info("error : {}, num : {}", error.getMessage(), num))
                .subscribe(data -> log.info("# onNext : {}", data),
                        error -> log.info("# onError : {}", error));

    }

}
