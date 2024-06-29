package study.jpa.repository.order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.jpa.dto.OrderFlatDto;
import study.jpa.dto.OrderItemQueryDto;
import study.jpa.dto.OrderQueryDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    //jpql new 오퍼레이션에는 엔티티 컬렉션을 넣는 것은 구조상 안된다.
    //그래서 Order 컬렉션부분만 제외한 값들을 가져온뒤 추가 로직으로 컬렉션을 다른 쿼리로 가져와 추가한다 Setter
    private List<OrderQueryDto> findOrders() {

        return em.createQuery("select new study.jpa.dto.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o " +
                        "join o.member m " +
                        "join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new study.jpa.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi join oi.item i " +
                        "where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
    // 위 두 메서드를 조합하여 하나의 데이터로 조회
    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQueryDto> result = findOrders();
        result.forEach(e ->{
            List<OrderItemQueryDto> orderItems = findOrderItems(e.getOrderId());
            e.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryDto> findAllByDto_optimization() {

        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = result.stream()
                .map(e -> e.getOrderId()).toList();
        //orderId 들의 List를 추출하여 다음 쿼리문에 파라미터로 넣고 IN() 사용
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);
        //위 값을 key로 가지는 Map으로 Map<Long, List<OrderItemQueryDto>>로 반환

        result.forEach(e -> e.setOrderItems(orderItemMap.get(e.getOrderId())));
        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery("select new study.jpa.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi join oi.item i " +
                        "where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(e -> e.getOrderId()));
        return orderItemMap;
    }


    public List<OrderFlatDto> findAllBy_flat() {
        List<OrderFlatDto> resultList = em.createQuery("select " +
                        "new  study.jpa.dto.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d " +
                        "join orderItems oi " +
                        "join oi.item i ", OrderFlatDto.class)
                .getResultList();
        //해당 쿼리 켤과의 경우 실질적 DB 쿼리문으로 가면 중복도 같이 조회되서 나온다.
        // 이 경우 distinct가 아닌 직접 걸러야한다.
        return resultList;
    }
}
