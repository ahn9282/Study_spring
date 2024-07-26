package study.hello.reactor.hello.operator.handlingerror;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OnErrorReturn {

    public static void main(String[] args) {

        getBooks()
                .map(e -> e.getName().toUpperCase())
                .onErrorReturn("No pen Name")
                .subscribe(data -> log.info("# onNext : {}", data));
    }

    private static Flux<Book> getBooks() {
        List<Book> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Book book = new Book("book" + i);
            list.add(book);
        }
        list.add(new Book(null));
        return Flux.fromIterable(list);
    }
    @Data
    private static class Book{
        private String name;

        public Book() {
        }

        public Book(String name) {
            this.name = name;
        }
    }
}
