package study.hello.reactor.hello.basic.start_ex;

import reactor.core.publisher.Flux;

public class ExampleFlux4 {
    public static void main(String[] args) {

        Flux.concat(
                        Flux.just("Mercury", "Venus", "Earth"),
                        Flux.just("Mars", "Jupiter", "Saturn"),
                        Flux.just("Uranus", "Neptune", "Pluto")
                )
                .collectList()
                .subscribe(System.out::println);


    }
}
