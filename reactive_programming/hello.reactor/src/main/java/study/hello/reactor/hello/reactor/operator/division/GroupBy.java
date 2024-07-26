package study.hello.reactor.hello.operator.division;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GroupBy {

    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            books.add(new Book("book" + i, i * 500, i));
        }

        Flux.fromIterable(books)
                .groupBy(book -> book.getName())
                .flatMap(groupFlux -> groupFlux
                        .map(book -> book.getNum() + "번 도서 [" + book.getName() + "]")
                        .collectList())
                .subscribe(data -> log.info("# {}", data));
    }


    @Data
    private static class Book {
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
