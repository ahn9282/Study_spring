package study.hello.reactor.hello.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Context1 {

    public static void main(String[] args) throws InterruptedException {

        Mono
                .deferContextual(ctx ->
                        Mono
                                .just("Hello" + " " + ctx.get("firstName")))
                .doOnNext(data -> log.info("# just doOnNext : {}", data))
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .transformDeferredContextual(
                        (mono, n) -> mono.map(data -> data + " " + n.get("lastName"))
                )
                                .contextWrite(context -> context.put("lastName", "Jobs"))
                                .contextWrite(context -> context.put("firstName", "steve"))
                                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(100L);

    }
}
