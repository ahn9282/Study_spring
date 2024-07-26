package study.hello.reactor.hello.webflux.core_component;



import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class BookLogFilter implements WebFilter {
    //WebFlux에서 Filter로 사용

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        return chain
                .filter(exchange).doAfterTerminate(() -> {
            if (path.contains("books")) { //종료 시 response 조회하여 statuscode 랑 같이 출력
                log.info("path : {}, status : {}", path, exchange.getResponse().getStatusCode());
            }
        });
    }
}