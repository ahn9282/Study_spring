package study.hello.reactor.hello.operator.measuretime;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Slf4j
public class Elapsed2 {

    public static void main(String[] args) throws InterruptedException {

        WebClient webClient = WebClient.builder()
                .baseUrl("http://worldtimeapi.org/api/timezone/Asia/Seoul")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();


        Mono
                .defer(() -> Mono.just(
                        webClient.get().retrieve()
                                .bodyToMono(String.class)

                ))
                .repeat(4)
                .elapsed()
                .map(response -> {
                    DocumentContext jsonContext = JsonPath.parse(response.getT2());
                    String dateTime = jsonContext.read("$.datetime");
                    return Tuples.of(dateTime, response.getT1());
                })
                .subscribe(data -> log.info("now : {}, elapsed : {}", data.getT1(), data.getT2()));

        Thread.sleep(7000L);
    }
}
