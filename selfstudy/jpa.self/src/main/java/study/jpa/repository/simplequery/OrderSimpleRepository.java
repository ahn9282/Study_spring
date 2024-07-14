package study.jpa.repository.simplequery;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.jpa.dto.OrderSimpleQueryDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderSimpleRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return   em.createQuery("select" +
                        " new study.jpa.dto.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                        " from Order o" +
                        " join o.member m  join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }
}
