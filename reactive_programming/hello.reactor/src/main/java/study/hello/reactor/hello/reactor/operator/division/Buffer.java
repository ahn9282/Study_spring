package study.hello.reactor.hello.operator.division;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Buffer {

    public static void main(String[] args) {

        Flux.range(1, 95)
                .buffer(10)
                .subscribe(buffer -> log.info("# onNext : {}", buffer));
    }
}
