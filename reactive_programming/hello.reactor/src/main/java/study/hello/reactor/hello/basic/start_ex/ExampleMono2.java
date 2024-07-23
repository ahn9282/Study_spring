package study.hello.reactor.hello.basic.start_ex;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ExampleMono2 {
    public static void main(String[] args) throws InterruptedException {

        WebClient webClient = WebClient.builder()
                .baseUrl("http://worldtimeapi.org/api/timezone/Asia/Seoul")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();


        Mono<String> responseMono = webClient.get()
                .retrieve()
                .bodyToMono(String.class);

        responseMono.map(response -> {
                    DocumentContext jsonContext = JsonPath.parse(response);
                    String dateTime = jsonContext.read("$.datetime");
                    return dateTime;
                })
                .subscribe(
                        data -> System.out.println("# emitted data : " + data),
                        error -> System.out.println(error),
                        () -> System.out.println("#emitted onComplete signal")
                );
    }
}
