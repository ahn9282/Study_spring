package hello.jpa.study2;

import hello.jpa.study2.domain.*;
import hello.jpa.study2.domain.item.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();

    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;
        public void dbInit1(){
            Member member1 = createMember("userA", "서울", "1", "11111");
            em.persist(member1);

            Book book1 = getBook("수학의 정석", 10000,100);
            Book book2 = getBook("자바의 정석", 20000, 100);

            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member1);

            Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);
            em.persist(order);
            Member member2 = createMember("UserB", "서율", "길바닥", "123");
            em.persist(member2);

            Book book3 = getBook("영단어", 15000,100);
            Book book4 = getBook("컨테이너 빌드업!", 40000,100);

            em.persist(book3);
            em.persist(book4);

            Delivery delivery2 = getDelivery(member2);

            OrderItem orderItem3 = OrderItem.createOrderItem(book3, 45000, 3);
            OrderItem orderItem4 = OrderItem.createOrderItem(book4, 120000, 4);
            Order order1 = Order.createOrder(member2, delivery2, orderItem3, orderItem4);
            em.persist(order1);
        }

        private static Delivery getDelivery(Member member2) {
            Delivery delivery2 = new Delivery();
            delivery2.setAddress(member2.getAddress());
            return delivery2;
        }

        private static Book getBook(String name, int price, int quantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(quantity);
            return book1;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

    };

}

