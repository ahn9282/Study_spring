package study.hello.reactor.hello.operator.handlingerror;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OnErrorResume {

    public static void main(String[] args) {

        final String keyword = "book";
        getBooks(keyword)
                .onErrorResume(error -> Mono.error(error))
                .subscribe(data -> log.info("# on Next : {}", data.getName()),
                        error -> log.info("# onError : {}", error));

    }



    private static Flux<Book> getBooks(final String keyword) {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Book book = new Book("book" + i, 100 * i, (i/10) + (i % 10));
            books.add(book);
        }
        books.add(new Book(null, 12345, 12345));
        return Flux.fromIterable(books)
                .filter(book -> book.getName().contains(keyword))
                .switchIfEmpty(Flux.error(new NoSuchBookException("No Exist!!")));
    }

    @Data
    private static class Book{
        private String name;
        private Integer price;
        private Integer num;

        public Book() {
        }

        public Book(String name, Integer price, Integer num) {
            this.name = name;
            this.price = price;
            this.num = num;
        }

        public Book(String name) {
            this.name = name;
        }
    }

    private static class NoSuchBookException extends RuntimeException {
        public NoSuchBookException(String message) {
            super(message);

        }
    }
}
