package hello.jpa.study2.api;

import hello.jpa.study2.domain.Address;
import hello.jpa.study2.domain.Order;
import hello.jpa.study2.domain.OrderItem;
import hello.jpa.study2.domain.OrderStatus;
import hello.jpa.study2.repository.OrderRepository;
import hello.jpa.study2.repository.OrderSearch;
import hello.jpa.study2.repository.order.query.OrderFlatDto;
import hello.jpa.study2.repository.order.query.OrderItemQueryDto;
import hello.jpa.study2.repository.order.query.OrderQueryDto;
import hello.jpa.study2.repository.order.query.OrderQueryRepository;
import hello.jpa.study2.repository.order.simplequery.SimpleOrderQueryDto;
import hello.jpa.study2.service.query.OrderQueryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderQueryService orderQueryService;
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            order.getOrderItems().stream()
                    .forEach(e -> e.getItem().getName());

        }
        return all;
    }
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        List<OrderDto> collect = all.stream()
                .map(e -> new OrderDto(e))
                .collect(toList());
        return collect;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
      return  orderQueryService.ordersV3();
    }

    //ToOne 관계는 fetchJoin 으로 페이징,
    // ToMany 관계는 batch를 통해 추가적인 일괄 추가 쿼리로 페이징하는 방식
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value="offset", defaultValue="0") int offset,
                                        @RequestParam(value="limit", defaultValue="10") int limit) {
        return  orderRepository.findAllWithMemberDelivery(offset,limit ).stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    //====================== v4 ~ v6  DTO 조회 방식 ==========================================

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> orderV4(){
       return orderQueryRepository.findOrderQueryDtos();
    }


    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> orderV5(){
        return orderQueryRepository.findAllByDto_optimization();
    }  @GetMapping("/api/v6/orders")

    public List<OrderQueryDto> orderV6(){
        //해당 경우에는 쿼리문이 한번으로 된다는 장점이 있지만, 중복을 걸러내지 않고 그대로 드러내 해당 결과에서 중복을 직접 걸러낸다.
        List<OrderFlatDto> flats = orderQueryRepository.finAllByDto_flat();

        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }



    @Getter
    public static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems  = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(toList());
            //db에 로딩시켜 프록시로 null 뜬 상황을 해결
        }
    }

    @Getter
    static class OrderItemDto {
        private String name;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            this.name = orderItem.getItem().getName();
            this.orderPrice = orderItem.getTotalPrice();
            this.count = orderItem.getCount();
        }
    }
}
