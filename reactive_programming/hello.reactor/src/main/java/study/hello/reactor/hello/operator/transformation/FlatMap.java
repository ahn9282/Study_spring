package study.hello.reactor.hello.operator.transformation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FlatMap {
    public static void main(String[] args) {
        Flux
                .just("Good", "Bad")
                .flatMap(e -> Flux   //innerSequence 생성하여 (외부 시퀀스 * 내부 시퀀스)만큼 emit
                        .just("Morning", "Afternoon", "Evening")
                        .map(time -> e + " " + time))
                .subscribe(data -> log.info("# onNext : {}", data));

    }
}
