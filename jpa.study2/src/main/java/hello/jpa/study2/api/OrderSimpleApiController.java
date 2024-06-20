package hello.jpa.study2.api;


import hello.jpa.study2.domain.Order;
import hello.jpa.study2.repository.OrderRepository;
import hello.jpa.study2.repository.OrderSearch;
import hello.jpa.study2.repository.order.simplequery.OrderSimpleQueryRepository;
import hello.jpa.study2.repository.order.simplequery.SimpleOrderQueryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    // 아무런 설정 없이 할 경우 무한루프 발생 비슷한 예시 : ToString 객체 간 연관관계로 인하여 양방향 연관관계 문제가 발생
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }//설정을 통해 proxy 객체를 막아 null 로 표시된다.
        // 그러나 이렇게 db에 호출한다면 진짜 Entity 객체를 DB에 요청하기에 proxy가 더이상 아니여서 json으로 출력된다.
        return all;
    }

    //여기서는 jpql 하나만 실행하였으나 실제 쿼리는 6번 실행된다. 이를 n + 1 문제라 하며 이는 성능에 있어 필히 다룰줄 알아야 함

    /**
     * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderQueryDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<SimpleOrderQueryDto> result = orders.stream()
                .map(o -> new SimpleOrderQueryDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderQueryDto> ordersV3() {
        List<SimpleOrderQueryDto> collect = orderRepository.findAllWithMemberDelivery().stream()
                .map(SimpleOrderQueryDto::new)
                .collect(toList());
        for (SimpleOrderQueryDto d : collect) {
            log.info("data : {}", d);
        }
        return collect;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }


}
