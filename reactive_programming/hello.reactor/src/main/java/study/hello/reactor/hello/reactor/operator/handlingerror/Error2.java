package study.hello.reactor.hello.operator.handlingerror;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.zip.DataFormatException;


@Slf4j
public class Error2 {

    public static void main(String[] args) {
        Flux
                .just('a', 'b', 'c', 'd', '3')
                .flatMap(letter -> {
                    try {
                        return convert(letter);
                    } catch (Exception e) {
                        return Flux.error(e);
                    }
                })
                .subscribe(data -> log.info("# onNext : {}", data),
                        error -> log.error("# onError", error));
    }

        private static Mono<String> convert ( char ch)throws DataFormatException {
            if (!Character.isAlphabetic(ch)) {
                throw new DataFormatException("Not Alphabetic");
            }
            return Mono.just("Converted to " + Character.toUpperCase(ch));
        }

}
