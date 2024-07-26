package study.hello.reactor.hello.reactor.backpressure;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class Example1 {
    public static void main(String[] args) {

        Flux<Integer>
                range = Flux
                .range(1, 5).doOnRequest(e -> log.info("# doOnRequest : {}", e));
        range.subscribe(new BaseSubscriber<Integer>(){
            @Override
            protected void hookOnSubscribe(Subscription subscription){
                request(1);
            }


            @SneakyThrows
            @Override
            protected void hookOnNext(Integer value) {
                Thread.sleep(2000L);
                log.info("# hookOnNext : {}", value);
                request(1);

            }
        });
    }
}
