package study.jpa.api;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.jpa.domain.Address;
import study.jpa.domain.Order;
import study.jpa.domain.OrderSearch;
import study.jpa.domain.OrderStatus;
import study.jpa.dto.OrderSimpleQueryDto;
import study.jpa.repository.OrderRepository;
import study.jpa.repository.simplequery.OrderSimpleRepository;

import java.time.LocalDateTime;
import java.util.List;

/*
XToOne 관계 : ManyToOne, OneToOne
 * Order
 * Order -> Member
 * Order -> Delivery
 * */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleRepository orderSimpleRepository;

    //엔티티 통째로 조회 연관관계로 인한 무한 루푸
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    
    //V2 : 엔티티를 DTO로 반환
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<SimpleOrderDto> collect = orders.stream()
               .map(SimpleOrderDto::new)
               .toList();
        return collect;
    }

    //V3 : Fetch 조인 으로 최적화한 엔티티를 DTO로 반환
    //많이 사용할 것같은 부분 중 하나 권장된다고 봐도 될듯 하다.
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .toList();
    }

    //V4 : JPA에서 DTO로 즉시 조회
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderSimpleRepository.findOrderDtos();
    }//이경우 jpql에서 dto를 생성과 반환을 동시에 하기에 dto가 고정되어 있어 정확히 원하는 데이터를 골라낼 수 있지만
    //dto가 고정되어 재사용성이 어렵다.
    //그러나 이 경우 반환하는 dto 생성자 파라미터만을 select 컬럼으로 가져와 컬럼 수를 최소한으로 하여 단일 성능은 뛰어나다.
    //그러나 컬럼의 수가 극적으로 차이나지 않는다면 성능차이는 생각보다 거의 없고 이보다는 join에서 성능을 더 영향을 끼친다.
    //그래서 우선적으로는 v3이고 그 다음엔 V4가 권장된다 보면 된다.


    @Data
    static  class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();

        }

    }}
