package study.hello.reactor.hello.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class ContextView3 {

    public static void main(String[] args) throws InterruptedException {

        final String key1 = "company";
        final String key2 = "firstName";
        final String key3 = "lastName";

        Mono
                .deferContextual(ctx ->
                        Mono.just(ctx.get(key1) + ", " + ctx.getOrEmpty(key2).orElse("no firstName") + " " + ctx.getOrDefault(key3, "no lastName")))
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key1, "Apple"))
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(100L);
        
    }
}
