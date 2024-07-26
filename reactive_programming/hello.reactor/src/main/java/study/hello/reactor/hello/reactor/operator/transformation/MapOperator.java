package study.hello.reactor.hello.operator.transformation;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class MapOperator {
    public static void main(String[] args) {

        Flux
                .just("1-Circle", "3-Circle", "5-Circle")
                .map(circle -> circle.replace("Circle", "rectangle"))
                .subscribe(data -> log.info("# onNext : {}", data));
    }
}
