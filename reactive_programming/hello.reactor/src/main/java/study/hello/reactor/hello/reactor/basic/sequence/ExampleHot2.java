package study.hello.reactor.hello.reactor.basic.sequence;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class ExampleHot2 {
    public static void main(String[] args) throws InterruptedException {


        WebClient webClient = WebClient.builder()
                .baseUrl("http://worldtimeapi.org/api/timezone/Asia/Seoul")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        Mono<String> mono = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .map(e -> {
                    DocumentContext jsonContext = JsonPath.parse(e);
                    log.info("json Data : {}", jsonContext.jsonString());
                    String dateTime = jsonContext.read("$.datetime");
                    return dateTime;
                }).cache();

        mono.subscribe(e -> log.info("# dateTime 1 : {}", e));
        Thread.sleep(2000);
        mono.subscribe(e -> log.info("# dateTime 2 : {}", e));

        Thread.sleep(2000);
    }
}
