package study.hello.reactor.hello.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class Context7 {
    public static final String HEADER_AUTH_TOKEN = "authToken";
    public static void main(String[] args) throws InterruptedException {

        Mono<String> mono =
                postBook(Mono.just(new Book("abcd-1111-3533-2809", "Reactor's Book", "Kevin"))
                )
                        .contextWrite(Context.of(HEADER_AUTH_TOKEN, "sqWWASfa"));

        mono.subscribe(data -> log.info("# onNext : {}", data));

        
        Thread.sleep(100L);
        
    }

    private static Mono<String> postBook(Mono<Book> book) {
        return Mono
                .zip(book, Mono
                        .deferContextual(ctx -> Mono.just(ctx.get(HEADER_AUTH_TOKEN)))
                )
                .flatMap(tuple -> {
                    String response = "POST the book(" + tuple.getT1().getBookName() + ", " + tuple.getT1().getAuthor() + ") with token : " + tuple.getT2();
                    return Mono.just(response);
                });
    }
}

@AllArgsConstructor
@Data
class Book{
    private String isbn;
    private String bookName;
    private String author;
}
