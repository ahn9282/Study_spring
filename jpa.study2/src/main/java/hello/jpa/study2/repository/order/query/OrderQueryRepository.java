package hello.jpa.study2.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto>  findOrderQueryDtos() {

        List<OrderQueryDto> result = findOrders();
        result.forEach(e -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(e.getOrderId());
            e.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new hello.jpa.study2.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi " +
                        "join oi.item i " +
                        "where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();

    }

    public List<OrderQueryDto> findOrders() {
        return em.createQuery("select new hello.jpa.study2.repository.order.query.OrderQueryDto(" +
                        " o.id, m.name, o.orderDate, o.status, d.address) " +
                        "from Order o" +
                        " join o.member m " +
                        " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();
        List<Long> orderIds = result.stream()
                .map(OrderQueryDto::getOrderId)
                .toList();

        List<OrderItemQueryDto> orderItems = em.createQuery("select " +
                        "new hello.jpa.study2.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        " from OrderItem oi " +
                        "join oi.item i " +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        //기본키 id, 객체 배열의 구조 Map 으로 가공(?)
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));

         result.forEach(e -> e.setOrderItems(orderItemMap.get(e.getOrderId())));
         return result;
    }

    public List<OrderFlatDto> finAllByDto_flat(){
       return  em.createQuery("select  " +
                       " new hello.jpa.study2.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
                        " from Order o " +
                        " join o.member m " +
                        " join o.delivery d " +
                        " join o.orderItems oi " +
                        " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
