package study.hello.reactor.hello.reactor.basic.start_ex;

import reactor.core.publisher.Mono;

public class ExampleMono1 {
    public static void main(String[] args) {
        Mono.just("Hello Reactor")
                .subscribe(e -> System.out.println("result = " + e));

        Mono.empty()
                .subscribe(
                        none -> System.out.println("#emitted onNext signal"),
                        error -> {},
                        () -> System.out.println("# emitted onComplete signal")
                );
    }
}
