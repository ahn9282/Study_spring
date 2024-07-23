package study.hello.reactor.hello.basic.start_ex;

import reactor.core.publisher.Flux;

public class ExampleFlux2 {
    public static void main(String[] args) {

        Flux.just(6, 9, 13)
                .map(e -> e % 2)
                .subscribe(System.out::println);

        System.out.println("====================== other part =========================");
        Flux
                .fromArray(new Integer[]{3, 6, 7, 9, 89})
                .filter(e -> e % 2 == 1)
                .map(e -> e * 2)
                .subscribe(System.out::println);


        System.out.println("====================== other part =========================");
        Flux
                .fromArray(new Integer[]{3, 6, 7, 9, 89})
                .filter(e -> e%2 < 6)
                .map(e -> e * 2)
                .subscribe(System.out::println);
    }
}
