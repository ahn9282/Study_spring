package study.hello.reactor.hello.basic.start_ex;

import reactor.core.publisher.Flux;

public class ExampleFlux1 {
    public static void main(String[] args) {
        Flux<String> sequence = Flux.just("Hello", "Reactor");//제공
        sequence.map(data -> data.toLowerCase())// 가공
                .subscribe(data -> System.out.println("data = " + data));//처리


        Flux<String> flow = Flux.just("First", "Second", "Third");
        flow.map(e -> e.toUpperCase())
                .subscribe(e -> System.out.print(e + "\t"));
        System.out.println();

        Flux<String> flow2 = Flux.just("First", "Second", "Third");
        flow2.filter(e -> e.length() == 5)
                .map(e -> e.concat(e))
                .subscribe(e -> System.out.println(e));
    }
}
