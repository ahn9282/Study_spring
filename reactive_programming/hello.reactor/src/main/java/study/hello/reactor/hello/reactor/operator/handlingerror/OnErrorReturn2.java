package study.hello.reactor.hello.operator.handlingerror;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

@Slf4j
public class OnErrorReturn2 {

    public static void main(String[] args) {

        getBooks()
                .map(e -> e.getName().toUpperCase())
                .onErrorReturn(NullPointerException.class,"No pen Name" )
                .onErrorReturn(IllegalFormatException.class, "Illegal pen Name")
                .subscribe(data -> log.info("# onNext : {}", data));
    }

    private static Flux<Book> getBooks() {
        List<Book> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Book book = new Book("book" + i);
            list.add(book);
        }
        list.add(new Book(null));
        list.add(new Book("*"));
        for (int i = 1; i <= 100; i++) {
            Book book = new Book("book" + i+" - " + i);
            list.add(book);
        }
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
