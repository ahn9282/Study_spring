package study.hello.reactor.hello.basic.start_ex;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ExampleFlux3 {
    public static void main(String[] args) {

        Flux<String> flux = Mono
                .justOrEmpty("Steve")
                .concatWith(Mono.justOrEmpty("Jobs"));

        flux.subscribe(System.out::println);

    }
}
