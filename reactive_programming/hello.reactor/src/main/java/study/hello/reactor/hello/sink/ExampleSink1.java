package study.hello.reactor.hello.sink;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

@Slf4j
public class ExampleSink1 {

    public static void main(String[] args) throws InterruptedException {

        int tasks = 6;

        Flux.create(sink -> {
                    IntStream.range(1, tasks)
                            .forEach(n -> sink.next(doTask(n)));
                    sink.complete();  // 스트림 종료
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(n -> log.info("# create() : {}", n))
                .publishOn(Schedulers.parallel())
                .map(result -> result + " success!!")
                .doOnNext(n -> log.info("# map() : {}", n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(500L);
    }

    private static String doTask(int num) {
        return "task " + num + " result";
    }
}
