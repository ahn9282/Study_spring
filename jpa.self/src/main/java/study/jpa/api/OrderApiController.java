package study.jpa.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.jpa.domain.*;
import study.jpa.dto.OrderFlatDto;
import study.jpa.dto.OrderItemQueryDto;
import study.jpa.dto.OrderQueryDto;
import study.jpa.repository.OrderRepository;
import study.jpa.repository.order.OrderQueryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream()
                    .forEach(o -> o.getItem().getName());
        }
        return orders;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
       return  orderRepository.findAll(new OrderSearch()).stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    //일 대 다 관계 패치 조인의 경우 페이징(.setFirstResult(), .setMaxResult()) 가 적용되지 않는다.
    //그래서 일대다 관계는 패치 조인에서 제외하고 페이징한 엔티티의 리스트에 람다를 통해 일대다 관계 엔티티들을 setter 매핑한다.
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        for (Order order : orders) {
            System.out.println("order ref = " + order + ", id = " + order.getId());
        }
       return orders.stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    //V3.1 엔티티를 DTO 변환 페이징과 한계돌파
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value="offset", defaultValue ="0" )int offset,
                                        @RequestParam(value="limit", defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllWithItem_ex(offset,limit);
        for (Order order : orders) {
            System.out.println("order ref = " + order + ", id = " + order.getId());
        }
        return orders.stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {

        return orderQueryRepository.findOrderQueryDtos();
    }
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {

        return orderQueryRepository.findAllByDto_optimization();
    }

    //쿼리 한번으로 뽑고 스트림을 통해 중복제거 그러나 중복제거 전이 jpql 생성, 실행이므로 페이징은 불가하다.
    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllBy_flat();
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

    @Data
    static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(toList());
            //여기소도 엔티티의 리스트일 경우 엔티티 자체를 반환하기도 하기에 null로 뜬다.
            //그래서 dto 리스트로 먼저 감싸 원하는 데이터만 가져오도록 한다.
        }
    }

}
