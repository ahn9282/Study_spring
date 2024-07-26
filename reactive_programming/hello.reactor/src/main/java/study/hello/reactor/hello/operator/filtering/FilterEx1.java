package study.hello.reactor.hello.operator.filtering;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FilterEx1 {
    public static void main(String[] args) {

        Flux
                .range(5, 10)
                .filter(e -> e % 2 == 1)
                .subscribe(data -> log.info("# onNext : {}", data));
    }
}
