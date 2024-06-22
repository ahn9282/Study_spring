package hello.jpa.study2.service;

import hello.jpa.study2.repository.ItemRepository;
import hello.jpa.study2.repository.MemberRepository;
import hello.jpa.study2.repository.OrderRepository;
import hello.jpa.study2.repository.OrderSearch;
import hello.jpa.study2.domain.Delivery;
import hello.jpa.study2.domain.Member;
import hello.jpa.study2.domain.Order;
import hello.jpa.study2.domain.OrderItem;
import hello.jpa.study2.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        //save 메서드에서 persist할 경우 cascade 설정을 통해 다른 엔티티가 속해있는 order의 필드 객체들고 persist가 전파된다.

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = (Order) orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();

    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
