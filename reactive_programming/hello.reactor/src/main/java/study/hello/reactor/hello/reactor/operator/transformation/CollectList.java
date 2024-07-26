package study.hello.reactor.hello.operator.transformation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CollectList {
    public static void main(String[] args) {
        Flux
                .just("...", "---", "...")
                .map(code -> "|" +(code) + "|")
                .collectList()
                .subscribe(list -> log.info(list.stream().collect(Collectors.joining())));

    }


}

