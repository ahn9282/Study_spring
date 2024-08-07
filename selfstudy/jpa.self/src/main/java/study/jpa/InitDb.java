package study.jpa;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.jpa.domain.*;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();

    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){
            Member member1 = createMember("userA", "서울", "1", "111");
            em.persist(member1);

            Book book1 = createBook("삼국지1", 10000, 100);
            em.persist(book1);
            Book book2 = createBook("삼국지2", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 1);
            Order order = Order.createOrder(member1, createDelivery(member1), orderItem1, orderItem2);
            em.persist(order);
        }
    public void dbInit2(){
        Member member1 = createMember("userB", "진주", "2", "222");
        em.persist(member1);

        Book book1 = createBook("무협지1", 20000, 200);
        em.persist(book1);
        Book book2 = createBook("무협지2", 40000, 300);
        em.persist(book2);

        Delivery delivery = createDelivery(member1);
        OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 2);
        OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 3);

        Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);
        em.persist(order);

    }

    private static Member createMember(String name, String city, String street, String zipcode) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address(city, street, zipcode));
        return member;
    }

    private static Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        return book;
    }

    private static Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        return delivery;
    }
    }

}
