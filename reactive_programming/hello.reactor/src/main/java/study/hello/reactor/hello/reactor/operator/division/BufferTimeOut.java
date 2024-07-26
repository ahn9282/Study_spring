package study.hello.reactor.hello.operator.division;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class BufferTimeOut {

    public static void main(String[] args) {

        Flux.range(1, 20)
                .map(num -> {
                    try {
                        if (num < 10) {
                            Thread.sleep(100L);
                        } else {
                            Thread.sleep(300L);
                        }
                    } catch (Exception e) {
                    }
                    return num;
                })
                .bufferTimeout(3, Duration.ofMillis(400L)) // 최대 3개 그러나 0.4초가 초과되면 대기 없이 flush
                .subscribe(buffer -> log.info("# onNext : {}", buffer));
    }
}
