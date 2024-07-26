package study.hello.reactor.hello.operator.create;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;


@Slf4j
public class Using {

    public static void main(String[] args) throws InterruptedException {

        Path path = Paths.get("C:\\study_Dev\\testUsing.txt");
        CountDownLatch latch = new CountDownLatch(1);

        Flux
                .using(() -> new PrintWriter(Files.newBufferedWriter(path, StandardOpenOption.APPEND)),
                        writer -> {
                            writer.println("New line added to file");
                            writer.flush();
                            return Flux.just("Write complete");
                        },
                        PrintWriter::close)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(result -> log.info("# onNext : {}", result),
                        error -> log.info("# onError!"),
                        () -> latch.countDown());

        Flux
                .using(() -> Files.lines(path), Flux::fromStream, Stream::close)
                .subscribe(log::info);
    }
}
