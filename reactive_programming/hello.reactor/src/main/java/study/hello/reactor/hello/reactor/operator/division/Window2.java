package study.hello.reactor.hello.operator.division;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.math.MathFlux;
import study.hello.reactor.hello.InitTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Window2 {

    public static void main(String[] args) {

        InitTest t = new InitTest();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i+1);
        }

        Flux
                .fromIterable(list)
                .window(5)
                .flatMap(flux -> MathFlux.sumInt(flux))
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        subscription.request(4);
                    }


                    @Override
                    protected void hookOnNext(Object value) {
                        log.info("# onNext : {}", value);
                        request(5);
                    }
                });
    }
}
