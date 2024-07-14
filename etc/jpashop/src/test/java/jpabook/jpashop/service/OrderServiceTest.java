package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        Member member = createMember();

        Book book = createBook("무협지", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertThat(OrderStatus.ORDER).isEqualByComparingTo(getOrder.getStatus());
        assertThat(getOrder.getOrderItems().size()).isEqualByComparingTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualByComparingTo(10000 * orderCount);
        assertThat(book.getStockQuantity()).isEqualByComparingTo(8);
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        Member member = createMember();
        Book book = createBook("수학의 정석", 15000, 15);

        int orderCount = 20;
        try {
            orderService.order(member.getId(), book.getId(), orderCount);

        } catch (NotEnoughStockException e) {
            return;
        }
        fail("재고 부족 예외 떠야함");

    }

    @Test
    public void 주문취소() throws Exception {

        Member member = createMember();
        Book item = createBook("book", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);


        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    private Book createBook(String itemName, int price, int quantity) {

        Book book = new Book();
        book.setName(itemName);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원 1");
        member.setAddress(new Address("서울", "강변", "123-123"));
        em.persist(member);
        return member;
    }


}